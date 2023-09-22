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
            return AuthResponseDTO.builder()
                .token(jwtService.genToken(user))
                .message("Inicio de sesion realizado correctamente.")
                .statusCode(HttpStatus.OK.value())
                .build();
        } catch (AuthenticationException e) {
            return new ResponseDTO("Username o password invalidos", HttpStatus.UNAUTHORIZED.value());
        }
    }

    public ResponseDTO register(RegisterRequestDTO request) {
        try {
            // Comprobar si el nombre de usuario o el correo electrónico ya está en uso
            if(userRepository.findByUsername(request.getUsername()).isPresent()) {
                System.out.println("1");
                return new ResponseDTO("El username ya esta en uso.", HttpStatus.BAD_REQUEST.value());
            }
            if(userRepository.findByEmail(request.getEmail()).isPresent()) {
                System.out.println("2");
                return new ResponseDTO("El email ya esta en uso.", HttpStatus.BAD_REQUEST.value());
            }
            // Crear el usuario con los datos de la petición y el rol de usuario por defecto (USER)
            UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .names(request.getNames())
                .surnames(request.getSurnames())
                .profileImg(request.getProfileImg())
                .roles(setRoleUser())
                .build();
            // Guardar el usuario en la base de datos
            userRepository.save(user);
            // Devolver el token generado junto con el mensaje de éxito y el código de estado
            return AuthResponseDTO.builder()
                .token(jwtService.genToken(user))
                .message("Registro realizado correctamente.")
                .statusCode(HttpStatus.OK.value())
                .build();
        } catch(DataIntegrityViolationException e) {
            return new ResponseDTO("El username o el email ya se encuentran en uso.", HttpStatus.CONFLICT.value());
        } catch (Exception e) {
            return new ResponseDTO("Error al registrarse. Por favor, intenta de nuevo.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // ? Logout ? //
    public ResponseDTO logoutToken(String realToken) {
        try {
            if(!jwtService.isTokenBlacklisted(realToken)) {
                jwtService.addTokenToBlacklist(realToken);
                return new ResponseDTO("Desconectado con exito.", HttpStatus.OK.value());
            }
            else {
                return new ResponseDTO("El token ya se encuentra en la lista negra.", HttpStatus.BAD_REQUEST.value());
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

}
