package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {
    private final AuthService authService;

    public ResponseDTO processOAuth2User(OAuth2AuthenticationToken authentication) {
        OAuth2User oAuth2User = authentication.getPrincipal();

        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        String username = email.split("@")[0];
        String profileImg = (String) oAuth2User.getAttributes().get("avatar_url"); // Asumiendo que usas GitHub como proveedor de OAuth2

        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername(username);
        registerRequest.setEmail(email);
        registerRequest.setPassword(null); // Similarmente, maneja la contraseña según sea necesario para OAuth2
        registerRequest.setNames(name.split(" ")[0]); // Asumiendo que el nombre y el apellido están separados por un espacio
        registerRequest.setSurnames(name.split(" ")[1]); // Ajusta según sea necesario
        registerRequest.setProfileImg(profileImg);

        return authService.register(registerRequest, true);
    }
}
