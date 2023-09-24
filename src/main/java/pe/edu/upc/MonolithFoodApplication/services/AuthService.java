package pe.edu.upc.MonolithFoodApplication.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.RoleRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    
    public ResponseDTO login(LoginRequestDTO request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username no encontrado"));
            String generatedToken = jwtService.genToken(user);
            return new AuthResponseDTO("Inicio de sesión realizado correctamente.", HttpStatus.OK.value(), generatedToken);
        } catch (AuthenticationException e) {
            return new ResponseDTO("Nombre de usuario o contraseña inválidos", HttpStatus.UNAUTHORIZED.value());
        }
    }

    public ResponseDTO register(RegisterRequestDTO request) {
        try {
            // Comprobar si el nombre de usuario o el correo electrónico ya está en uso
            if(userRepository.findByUsername(request.getUsername()).isPresent()) {
                return new ResponseDTO("El nombre de usuario ya está en uso.", HttpStatus.BAD_REQUEST.value());
            }
            if(userRepository.findByEmail(request.getEmail()).isPresent()) {
                return new ResponseDTO("El email ya está en uso.", HttpStatus.BAD_REQUEST.value());
            }
            // Validar la contraseña segura
            ResponseDTO respuestaValidacion = validarContraseniaSegura(request.getPassword());
            // Si la contraseña no es segura, devolver la respuesta de validación
            if (respuestaValidacion != null) return respuestaValidacion;
            // Crear el usuario con los datos de la petición y el rol de usuario por defecto (USER) y lo guarda en la BD
            UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
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
        } catch(DataIntegrityViolationException e) {
            return new ResponseDTO("El nombre de usuario o el email ya se están en uso.", HttpStatus.CONFLICT.value());
        } catch (Exception e) {
            return new ResponseDTO("Error al registrarse. Por favor, intenta de nuevo.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ResponseDTO logoutToken(String realToken) {
        try {
            if(!jwtService.isTokenBlacklisted(realToken)) {
                jwtService.addTokenToBlacklist(realToken);
                return new ResponseDTO("Desconectado exitosamente.", HttpStatus.OK.value());
            }
            else {
                return new ResponseDTO("El token ya está inhabilitado.", HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            return new ResponseDTO("Error al desconectar. Por favor, intenta de nuevo.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private Set<RoleEntity> setRoleUser() {
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity USER = roleRepository.findByName(RoleEnum.USER);
        roles.add(USER);
        return roles;
    }

    public ResponseDTO validarContraseniaSegura(String contrasenia) {
        if (contrasenia.length() < 8) {
            return new ResponseDTO("La contraseña debe tener al menos 8 caracteres.", HttpStatus.BAD_REQUEST.value());
        }
    
        boolean tieneLetraMayuscula = false;
        boolean tieneLetraMinuscula = false;
        boolean tieneDigito = false;
        boolean tieneCaracterEspecial = false;
    
        for (char c : contrasenia.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneLetraMayuscula = true;
            } else if (Character.isLowerCase(c)) {
                tieneLetraMinuscula = true;
            } else if (Character.isDigit(c)) {
                tieneDigito = true;
            } else if ("~!@#$%^&*()_+-=[];,./{}|:?><".indexOf(c) >= 0) {
                tieneCaracterEspecial = true;
            }
        }
        
        if (!tieneLetraMayuscula) {
            return new ResponseDTO("La contraseña debe contener al menos una letra mayúscula.", HttpStatus.BAD_REQUEST.value());
        }
        if (!tieneLetraMinuscula) {
            return new ResponseDTO("La contraseña debe contener al menos una letra minúscula.", HttpStatus.BAD_REQUEST.value());
        }
        if (!tieneDigito) {
            return new ResponseDTO("La contraseña debe contener al menos un dígito numérico.", HttpStatus.BAD_REQUEST.value());
        }
        if (!tieneCaracterEspecial) {
            return new ResponseDTO("La contraseña debe contener al menos un carácter especial.", HttpStatus.BAD_REQUEST.value());
        }
    
        return null;
    }

}
