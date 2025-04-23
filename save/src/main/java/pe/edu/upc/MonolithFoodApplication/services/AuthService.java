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

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.IpLoginAttemptEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.WalletEntity;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.enums.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.repositories.IpLoginAttemptRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.RoleRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final IpLoginAttemptRepository ipLoginAttemptRepository;
    // private final ExternalApisService externalApisService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    // Variables de entorno
    @Value("${app.security.ip-block-duration-hours}")
    private Integer IP_BLOCK_DURATION_HOURS;
    @Value("${app.security.reset-attempt-duration-minutes}")
    private Integer RESET_ATTEMPT_DURATION_MINUTES;
    @Value("${app.security.max-attempts-login}")
    private Integer MAX_ATTEMPTS_LOGIN;

    // * Brian: Iniciar sesión
    @Transactional
    public ResponseDTO login(LoginRequestDTO request) {
        String ipAddress = getClientIp();

        try {
            UserEntity userEntity = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            // Si el usuario está bloqueado, devolver un error
            if (userEntity != null) {
                if (isIpBlocked(ipAddress, userEntity)) {
                    logger.warn("Acceso denegado para la IP {} hacia el usuario {}.", ipAddress, request.getUsername());
                    return new ResponseDTO("Tu acceso fue bloqueado", 403, ResponseType.ERROR);
                }
                if (userEntity.getIsOauthRegistered()) {
                    return new ResponseDTO("Usuario autenticado via OAuth", 401, ResponseType.ERROR);
                }
            }

            // Si el usuario y la contraseña son válidos, autenticar al usuario en el contexto de Spring Security y generar un token JWT para el usuario
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserEntity user = userRepository.findByUsername(request.getUsername()).get();
            UserConfigEntity userConfig = user.getUserConfig();
            
            // Reiniciar el contador de intentos fallidos de inicio de sesión
            IpLoginAttemptEntity attempt = ipLoginAttemptRepository.findByIpAddressAndUsername(ipAddress, request.getUsername()).orElse(null);
            if (attempt != null) {
                attempt.setAttemptsCount(1);
                ipLoginAttemptRepository.save(attempt);
            }

            // Retornar el token generado junto con el mensaje de éxito y el código de estado
            String profileStage = jwtService.determineProfileStage(user);
            String generatedToken = jwtService.genToken(user, profileStage);

            return new AuthResponseDTO("Inicio de sesión exitoso", 200, ResponseType.SUCCESS, generatedToken, userConfig.getDarkMode());
        } catch (AuthenticationException e) {
            UserEntity userEntity = userRepository.findByUsername(
                request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")
            );

            if (userEntity != null) {
                registerANewFailedAttempt(ipAddress, userEntity);
            }

            return new ResponseDTO("Datos inválidos", 401, ResponseType.ERROR);
        }
    }
    // * Brian: Registrar un nuevo usuario
    @Transactional
    public ResponseDTO register(RegisterRequestDTO request) {
        try {
            // Validaciones de longitud de los campos
            if (request.getUsername().length() > 40) {
                return new ResponseDTO("El nombre de usuario no debe exceder los 40 caracteres", 400, ResponseType.ERROR);
            }
            if (request.getEmail().length() > 150) {
                return new ResponseDTO("El email no debe exceder los 150 caracteres", 400, ResponseType.ERROR);
            }
            if (request.getNames().length() > 128) {
                return new ResponseDTO("El nombre completo no debe exceder los 128 caracteres", 400, ResponseType.ERROR);
            }

            // Comprobar si el nombre de usuario o el correo electrónico ya está en uso
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return new ResponseDTO("Nombre de usuario no disponible", 400, ResponseType.ERROR);
            }
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return new ResponseDTO("Email no disponible", 400, ResponseType.ERROR);
            }

            // Validar el nombre de usuario y la contraseña
            ResponseDTO validacionUsername = validarUsernameSeguro(request.getUsername());
            ResponseDTO validacionContrasenia = validarContraseniaSegura(request.getPassword());
            if (validacionUsername != null) {
                return validacionUsername;
            }
            if (validacionContrasenia != null) {
                return validacionContrasenia;
            }

            // Formato del correo electrónico
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(request.getEmail());

            if (!matcher.matches()) {
                return new ResponseDTO("Email invalido", 400, ResponseType.WARN);
            }
            
            UserConfigEntity uc = UserConfigEntity.builder()
                .notifications(false)
                .darkMode(true)
                .lastFoodEntry(null)
                .lastWeightUpdate(null)
                .build();

            String ipAddress = getClientIp();
            
            WalletEntity wallet = WalletEntity.builder()
                .balance(0.0)
                .currency("PEN")
                .currencyName("Sol")
                .currencySymbol("S/.")
                .build();

            UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .names(request.getNames())
                .profileImg("https://i.ibb.co/28FKMc7/monolithfood-img.png")
                .isOauthRegistered(false)
                .isAccountBlocked(false)
                .ipAddress(ipAddress)
                .userConfig(uc)
                .wallet(wallet)
                .roles(setRoleUser())
                .build();
            userRepository.save(user);
            
            String profileStage = "information";
            String token = jwtService.genToken(user, profileStage);
            
            return new AuthResponseDTO("Registro exitoso", 200, ResponseType.SUCCESS, token, uc.getDarkMode());
        } catch (DataIntegrityViolationException e) {
            return new ResponseDTO(
                "Datos inválidos", 409, ResponseType.ERROR);
        } catch (Exception e) {
            return new ResponseDTO(
                "Ocurrió un error", 500, ResponseType.ERROR);
        }
    }
    // * Brian: Cerrar sesión (desactivar el token)
    public ResponseDTO logout(String realToken) {
        try {
            if (!jwtService.isTokenBlacklisted(realToken)) {
                jwtService.addTokenToBlacklist(realToken);
                return new ResponseDTO("Desconectado exitosamente", 200, ResponseType.SUCCESS);
            } else {
                return new ResponseDTO("Token invalido", 400, ResponseType.ERROR);
            }
        } catch (Exception e) {
            return new ResponseDTO("Error al desconectar", 500, ResponseType.ERROR);
        }
    }

    // ? Funciones auxiliares
    public Set<RoleEntity> setRoleUser() {
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity USER = roleRepository.findByName(RoleEnum.USER).get();
        roles.add(USER);
        return roles;
    }
    private ResponseDTO validarUsernameSeguro(String username) {
        if (username.length() < 6) {
            return new ResponseDTO("El nombre de usuario debe tener al menos 6 caracteres", 400, ResponseType.WARN);
        }
        boolean tieneLetraMayuscula = false;
        // Recorrer cada caracter
        for (char c : username.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneLetraMayuscula = true;
                break;
            }
        }
        if (tieneLetraMayuscula) {
            return new ResponseDTO("El nombre de usuario do debe contener letras mayúsculas", 400, ResponseType.WARN);
        }
        return null;
    }

    private ResponseDTO validarContraseniaSegura(String contrasenia) {
        if (contrasenia.length() < 8) return new ResponseDTO("La contraseña debe tener al menos 8 caracteres", 400, ResponseType.WARN);
        boolean tieneLetraMayuscula = false;
        boolean tieneLetraMinuscula = false;
        boolean tieneDigito = false;
        boolean tieneCaracterEspecial = false;
        // Recorrer cada caracter
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
        if (!tieneLetraMayuscula)
            return new ResponseDTO("La contraseña debe contener al menos una letra mayúscula", 400, ResponseType.WARN);
        if (!tieneLetraMinuscula)
            return new ResponseDTO("La contraseña debe contener al menos una letra minúscula", 400, ResponseType.WARN);
        if (!tieneDigito)
            return new ResponseDTO("La contraseña debe contener al menos un dígito numérico", 400, ResponseType.WARN);
        if (!tieneCaracterEspecial)
            return new ResponseDTO("La contraseña debe contener al menos un carácter especial", 400, ResponseType.WARN);
        return null;
    }

    // FUNCIÓN: Registra un intento fallido de inicio de sesión
    private void registerANewFailedAttempt(String ipAddress, UserEntity userEntity) {
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
        ipLoginAttemptRepository.save(attempt);
    }

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

    public static String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }
}
