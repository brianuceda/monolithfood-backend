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
    
    public AuthResponseDTO login(LoginRequestDTO request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.genToken(user);
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
        userRepository.save(user);
        return AuthResponseDTO.builder()
            .token(jwtService.genToken(user))
            .build();
    }
    
    public String showEncondePasswords() {
        System.out.println("kiridepapel: " + passwordEncoder.encode("40se02j7"));
        System.out.println("heatherxvalencia: " + passwordEncoder.encode("mongolita123"));
        System.out.println("whoami: " + passwordEncoder.encode("godgod"));
        System.out.println("nayde: " + passwordEncoder.encode("gatitobello123"));
        System.out.println("gabi: " + passwordEncoder.encode("prolenvalorant"));
        System.out.println("impresora: " + passwordEncoder.encode("lenovo"));
        return "Ok";
    }

    // ? Logout ? //
    public void logoutToken(String realToken) {
        if(!jwtService.isTokenBlacklisted(realToken)) {
            jwtService.addTokenToBlacklist(realToken);
        }
    }

    private Set<RoleEntity> setRoleUser() {
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity USER = roleRepository.findByName(RoleEnum.USER);
        roles.add(USER);
        return roles;
    }

}
