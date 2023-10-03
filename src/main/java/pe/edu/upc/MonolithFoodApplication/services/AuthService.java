package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.IpLoginAttemptEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.IpLoginAttemptRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.RoleRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    // ? Atributos
    // Inyección de dependencias
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final IpLoginAttemptRepository ipLoginAttemptRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    // Variables de entorno
    @Value("${ip.block.duration.hours}")
    private Integer IP_BLOCK_DURATION_HOURS;
    @Value("${reset.attempt.duration.minutes}")
    private Integer RESET_ATTEMPT_DURATION_MINUTES;
    @Value("${max.attempts.login}")
    private Integer MAX_ATTEMPTS_LOGIN;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    // ? Metodos
    // * Brian: Iniciar sesión
    public ResponseDTO login(LoginRequestDTO request) {
        try {
            UserEntity userEntity = userRepository.findByUsername(request.getUsername()).orElseThrow(null);
            if (userEntity != null) {
                // Verificar si la IP está bloqueada
                if (isIpBlocked(request.getIpAddress(), userEntity)) {
                    logger.warn("Acceso denegado para la IP {} hacia el usuario {}.", request.getIpAddress(), request.getUsername());
                    return new ResponseDTO("Acceso bloqueado desde esta dirección IP", HttpStatus.FORBIDDEN.value());
                }
            }
            // Si el usuario y la contraseña son válidos, autenticar al usuario en el contexto de Spring Security y generar un token JWT para el usuario
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow(null);
            String generatedToken = jwtService.genToken(user);
            // Reiniciar el contador de intentos fallidos de inicio de sesión
            IpLoginAttemptEntity attempt = ipLoginAttemptRepository.findByIpAddressAndUsername(request.getIpAddress(), request.getUsername()).orElse(null);
            if (attempt != null) {
                attempt.setAttemptsCount(1);
                ipLoginAttemptRepository.save(attempt);
            }
            // Retornar el token generado junto con el mensaje de éxito y el código de estado
            return new AuthResponseDTO("Inicio de sesión realizado correctamente.", HttpStatus.OK.value(), generatedToken);
        } catch (AuthenticationException e) {
            // Si la autenticación falla, registrar el intento fallido
            UserEntity userEntity = userRepository.findByUsername(request.getUsername()).orElseThrow(null);
            if (userEntity != null)
                registerFailedAttempt(request.getIpAddress(), userEntity);
            return new ResponseDTO("Nombre de usuario o contraseña inválidos", HttpStatus.UNAUTHORIZED.value());
        }
    }
    // * Brian: Registrar un nuevo usuario
    public ResponseDTO register(RegisterRequestDTO request, Boolean isOAuth) {
        try {
            // Comprobar si el nombre de usuario o el correo electrónico ya está en uso
            if (userRepository.findByUsername(request.getUsername()).isPresent())
                return new ResponseDTO("El nombre de usuario ya está en uso.", HttpStatus.BAD_REQUEST.value());
            if (userRepository.findByEmail(request.getEmail()).isPresent())
                return new ResponseDTO("El email ya está en uso.", HttpStatus.BAD_REQUEST.value());
            // Obtener la contraseña del request (por oauth2, la contraseña es null)
            String password = request.getPassword();
            // Valida si la contraseña es segura o no, dependiendo de si el registro es por Oauth2 o no
            if (!isOAuth) {
            // Validar la contraseña segura si el registro no es por Oauth2
                ResponseDTO respuestaValidacion = validarContraseniaSegura(request.getPassword());
                // Si la contraseña no es segura, devolver la respuesta de validación
                if (respuestaValidacion != null)
                    return respuestaValidacion;
                // Genera una contraseña encriptada si el registro no es por Oauth2
                password = passwordEncoder.encode(request.getPassword());
            }
            // Validar formato del correo electrónico
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(request.getEmail());
            if (!matcher.matches()) {
                return new ResponseDTO("El formato del correo electrónico no es válido.", HttpStatus.BAD_REQUEST.value());
            }
            // Crear el usuario con los datos de la petición y el rol de usuario por defecto (USER) y lo guarda en la BD
            UserEntity user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(password)
                    .email(request.getEmail())
                    .names(request.getNames())
                    .surnames(request.getSurnames())
                    .profileImg(request.getProfileImg())
                    .roles(setRoleUser())
                    .build();
            userRepository.save(user);
            // Generar el token JWT para el usuario
            String generatedToken = jwtService.genToken(user);
            // Devolver el token generado junto con el mensaje de éxito y el código de estado
            return new AuthResponseDTO("Registro realizado correctamente.", HttpStatus.OK.value(), generatedToken);
        } catch (DataIntegrityViolationException e) {
            return new ResponseDTO("El nombre de usuario o el email ya se están en uso.", HttpStatus.CONFLICT.value());
        } catch (Exception e) {
            return new ResponseDTO("Error al registrarse. Por favor, intenta de nuevo.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    // * Brian: Cerrar sesión (desactivar el token)
    public ResponseDTO logout(String realToken) {
        try {
            if (!jwtService.isTokenBlacklisted(realToken)) {
                jwtService.addTokenToBlacklist(realToken);
                return new ResponseDTO("Desconectado exitosamente.", HttpStatus.OK.value());
            } else {
                return new ResponseDTO("El token ya está inhabilitado.", HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            return new ResponseDTO("Error al desconectar. Por favor, intenta de nuevo.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    // * Brian: Manejar la autenticación de Oauth2


    // ? Funciones auxiliares
    // FUNCIÓN: Devuelve un set con el rol USER para asignarlo a un nuevo usuario
    private Set<RoleEntity> setRoleUser() {
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity USER = roleRepository.findByName(RoleEnum.USER).get();
        roles.add(USER);
        return roles;
    }
    // FUNCIÓN: Valida que la contraseña sea segura
    public ResponseDTO validarContraseniaSegura(String contrasenia) {
        if (contrasenia.length() < 8) return new ResponseDTO("La contraseña debe tener al menos 8 caracteres.", HttpStatus.BAD_REQUEST.value());
        boolean tieneLetraMayuscula = false;
        boolean tieneLetraMinuscula = false;
        boolean tieneDigito = false;
        boolean tieneCaracterEspecial = false;
        // Recorrer cada caracter de la contraseña
        for (char c : contrasenia.toCharArray()) {
            if (Character.isUpperCase(c))
                tieneLetraMayuscula = true;
            else if (Character.isLowerCase(c))
                tieneLetraMinuscula = true;
            else if (Character.isDigit(c)) 
                tieneDigito = true;
            else if ("~!@#$%^&*()_+-=[];,./{}|:?><".indexOf(c) >= 0)
                tieneCaracterEspecial = true;
        }
        // Si la contraseña no cumple con los requisitos, devolver un mensaje de error
        if (!tieneLetraMayuscula)
            return new ResponseDTO("La contraseña debe contener al menos una letra mayúscula.", HttpStatus.BAD_REQUEST.value());
        if (!tieneLetraMinuscula)
            return new ResponseDTO("La contraseña debe contener al menos una letra minúscula.", HttpStatus.BAD_REQUEST.value());
        if (!tieneDigito)
            return new ResponseDTO("La contraseña debe contener al menos un dígito numérico.", HttpStatus.BAD_REQUEST.value());
        if (!tieneCaracterEspecial)
            return new ResponseDTO("La contraseña debe contener al menos un carácter especial.", HttpStatus.BAD_REQUEST.value());
        // Si la contraseña cumple con los requisitos, devolver null
        return null;
    }
    // FUNCIÓN: Registra un intento fallido de inicio de sesión
    private void registerFailedAttempt(String ipAddress, UserEntity userEntity) {
        IpLoginAttemptEntity attempt = ipLoginAttemptRepository.findByIpAddressAndUsername(ipAddress, userEntity.getUsername()).orElse(null);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long tenMinutesMillis = this.RESET_ATTEMPT_DURATION_MINUTES * 60 * 1000;
        // Si no hay un registro existente para esta IP y usuario
        if (attempt == null) {
            // Crear un nuevo registro
            attempt = new IpLoginAttemptEntity();
            attempt.setIpAddress(ipAddress);
            attempt.setIsIpBlocked(false);
            attempt.setLastAttemptDate(now);
            attempt.setAttemptsCount(1);
            UserEntity user = userRepository.findByUsername(userEntity.getUsername()).orElse(null);
            attempt.setUser(user);
        } else {
            // Si el último intento fue hace más de XX minutos
            if ((now.getTime() - attempt.getLastAttemptDate().getTime()) > tenMinutesMillis) 
                attempt.setAttemptsCount(1);
            else {
                attempt.setAttemptsCount(attempt.getAttemptsCount() + 1);
                if (attempt.getAttemptsCount() >= this.MAX_ATTEMPTS_LOGIN) {
                    // Bloquear la IP por XX horas
                    logger.warn("IP {} bloqueada para el usuario {}.", attempt.getIpAddress(), attempt.getUser().getUsername());
                    attempt.setIsIpBlocked(true);
                    attempt.setBlockedDate(now);
                }
            }
            attempt.setLastAttemptDate(now);
        }
        // Guarda todos los cambios en la BD
        ipLoginAttemptRepository.save(attempt);
    }
    // FUNCIÓN: Verifica si la IP está bloqueada o no
    private boolean isIpBlocked(String ipAddress, UserEntity userEntity) {
        IpLoginAttemptEntity attempt = ipLoginAttemptRepository.findByIpAddressAndUsername(ipAddress, userEntity.getUsername()).orElse(null);
        // Si no hay un registro existente en la BD para esta IP y este usuario, la IP
        // no está bloqueada
        if (attempt == null)
            return false;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long oneDayMillis = this.IP_BLOCK_DURATION_HOURS * 60 * 60 * 1000;
        // Si la IP está bloqueada y han pasado más de XX horas desde que se bloqueó
        if (attempt.getIsIpBlocked() && (now.getTime() - attempt.getBlockedDate().getTime()) > oneDayMillis) {
            // Desbloquear
            logger.info("IP {} desbloqueada para el usuario {}.", attempt.getIpAddress(), attempt.getUser().getUsername());
            attempt.setIsIpBlocked(false);
            attempt.setBlockedDate(null);
            attempt.setLastAttemptDate(now);
            attempt.setAttemptsCount(0);
            ipLoginAttemptRepository.save(attempt);
            return false;
        }
        // Devolver si la IP está bloqueada o no
        return attempt.getIsIpBlocked();
    }

}
