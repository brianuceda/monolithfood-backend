package pe.edu.upc.MonolithFoodApplication.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    
    public AuthResponseDTO login(LoginRequestDTO request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.genToken(user);
        
        System.out.println(user.getUsername() + "\n");
        System.out.println(user.getPassword() + "\n");
        System.out.println(token);

        return AuthResponseDTO.builder().token(token).build();
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {

        UserEntity user = UserEntity.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .names(request.getNames())
            .surnames(request.getSurnames())
            .profileImg(request.getProfileImg())
            .roles(setRoleUser())
            .build();

        System.out.println(user.getUsername() + "\n");
        System.out.println(user.getPassword() + "\n");
        System.out.println(user.getEmail() + "\n");
        System.out.println(user.getNames() + "\n");
        System.out.println(user.getSurnames() + "\n");
        System.out.println(user.getRoles() + "\n");

        userRepository.save(user);

        return AuthResponseDTO.builder()
            .token(jwtService.genToken(user))
            .build();
    }

    // ? Logout ? //
    public void logoutToken(String realToken) {
        if(!jwtService.isTokenBlacklisted(realToken)) {
            jwtService.addTokenToBlacklist(realToken);
        }
    }

    private Set<RoleEntity> setRoleUser() {
        // Crear un Set<RoleEntity> con un solo elemento "user"
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity userRole = new RoleEntity(null, RoleEnum.USER);
        roles.add(userRole);
        return roles;
    }

}
