package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Optional;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.OAuth2PrincipalDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.WalletEntity;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthService authService;
    
    @Transactional
    public ResponseDTO joinOAuth2(OAuth2AuthenticationToken authentication) {
        try {
            ResponseDTO response = new ResponseDTO();

            // Obtener los datos de autenticacion
            String provider = authentication.getAuthorizedClientRegistrationId();
            OAuth2User principal = authentication.getPrincipal();
            
            // Acceder a los atributos
            OAuth2PrincipalDTO oa2 = new OAuth2PrincipalDTO();
            
            if ("github".equals(provider)) {
                // Atributos obtenidos de GitHub
                oa2.setUsername(principal.getAttribute("login"));
                oa2.setPassword(null);
                oa2.setEmail(principal.getAttribute("email"));
                oa2.setNames(principal.getAttribute("name"));
                oa2.setProfileImg(principal.getAttribute("avatar_url"));
                oa2.setOauthProviderId(authentication.getName());
                oa2.setIsOauthRegistered(true);
                
                // Restricciones
                if (oa2.getUsername() == null) {
                    return new ResponseDTO("Error durante la autenticacion", 400, ResponseType.ERROR);
                }
                if (oa2.getNames() == null) {
                    oa2.setNames(oa2.getUsername().substring(0, 1).toUpperCase() + oa2.getUsername().substring(1));
                }
                if (oa2.getProfileImg() == null) {
                    oa2.setProfileImg("https://i.ibb.co/vvBKFjR/github-img.png");
                }

                oa2.setUsername("GH_" + (oa2.getUsername().substring(0, 1).toUpperCase() + (oa2.getUsername().substring(1)).toLowerCase()));
            } else if ("microsoft".equals(provider)) {
                String username = principal.getAttribute("displayName");
                if (username != null && !username.isEmpty()) {
                    oa2.setUsername(username.replaceAll("\\s", ""));
                }

                // Atributos obtenidos de Microsoft
                oa2.setPassword(null);
                oa2.setEmail(principal.getAttribute("mail"));
                oa2.setNames(principal.getAttribute("displayName"));
                oa2.setProfileImg(principal.getAttribute("picture"));
                oa2.setOauthProviderId(authentication.getName());
                oa2.setIsOauthRegistered(true);

                // Restricciones
                if (oa2.getUsername() == null) {
                    return new ResponseDTO("Error durante la autenticacion", 400, ResponseType.ERROR);
                }
                if (oa2.getNames() == null) {
                    oa2.setNames(oa2.getUsername().substring(0, 1).toUpperCase() + oa2.getUsername().substring(1));
                }
                if (oa2.getProfileImg() == null) {
                    oa2.setProfileImg("https://i.ibb.co/8B1kpZC/microsoft-img.png");
                }

                oa2.setUsername("MS_" + (oa2.getUsername().substring(0, 1).toUpperCase() + (oa2.getUsername().substring(1)).toLowerCase()));
            } else if ("google".equals(provider)) {
                // Atributos obtenidos de Google
                oa2.setUsername(principal.getAttribute("given_name"));
                oa2.setPassword(null);
                oa2.setEmail(principal.getAttribute("email"));
                oa2.setNames(principal.getAttribute("name"));
                oa2.setProfileImg(principal.getAttribute("picture"));
                oa2.setOauthProviderId(authentication.getName());
                oa2.setIsOauthRegistered(true);
                
                // Restricciones
                if (oa2.getUsername() == null) {
                    return new ResponseDTO("Error durante la autenticacion", 400, ResponseType.ERROR);
                }
                if (oa2.getNames() == null) {
                    oa2.setNames(oa2.getUsername().substring(0, 1).toUpperCase() + oa2.getUsername().substring(1));
                }
                if (oa2.getProfileImg() == null) {
                    oa2.setProfileImg("https://i.ibb.co/c1vgK6T/google-img.png");
                }

                oa2.setUsername("GG_" + (oa2.getUsername().substring(0, 1).toUpperCase() + (oa2.getUsername().substring(1)).toLowerCase()));
            } else {
                return new ResponseDTO("El proveedor no es válido", 400, ResponseType.ERROR);
            }
            
            Optional<UserEntity> oAuthUser = userRepository.findByOauthProviderId(oa2.getOauthProviderId());

            if (oAuthUser.isPresent())
                response = oAuth2Login(oAuthUser.get().getOauthProviderId());
            else if (!oAuthUser.isPresent()) {
                UserConfigEntity uc = UserConfigEntity.builder()
                    .notifications(false)
                    .darkMode(true)
                    .lastFoodEntry(null)
                    .lastWeightUpdate(null)
                    .build();

                WalletEntity wallet = WalletEntity.builder()
                    .balance(0.0)
                    .currency("PEN")
                    .currencyName("Sol")
                    .currencySymbol("S/.")
                    .build();
                
                UserEntity user = UserEntity.builder()
                    .username(oa2.getUsername())
                    .password(null)
                    .email(oa2.getEmail())
                    .names(oa2.getNames())
                    .profileImg(oa2.getProfileImg())
                    .oauthProviderId(oa2.getOauthProviderId())
                    .isOauthRegistered(oa2.getIsOauthRegistered())
                    .isAccountBlocked(false)
                    .ipAddress(AuthService.getClientIp())
                    .userConfig(uc)
                    .wallet(wallet)
                    .roles(authService.setRoleUser())
                    .build();

                response = oAuth2Register(user);
            } else {
                return new ResponseDTO("Ocurrió un error externo", 500, ResponseType.ERROR);
            }

            return response;
        } catch (Exception e) {
            return new ResponseDTO("Ocurrió un error interno: " + e.getMessage() + " - " + e.getStackTrace().toString(), 500, ResponseType.ERROR);
        }
    }

    private ResponseDTO oAuth2Login(String oAuthProviderId) {
        UserEntity user = userRepository.findByOauthProviderId(oAuthProviderId).get();

        String profileStage = jwtService.determineProfileStage(user);
        String token = jwtService.genToken(user, profileStage);
        Boolean darkMode = user.getUserConfig().getDarkMode();

        return new AuthResponseDTO("Inicio de sesión exitoso", 200, ResponseType.SUCCESS, token, darkMode);
    }

    private ResponseDTO oAuth2Register(UserEntity user) {
        userRepository.save(user);

        String profileStage = "information";
        String token = jwtService.genToken(user, profileStage);
        Boolean darkMode = user.getUserConfig().getDarkMode();

        return new AuthResponseDTO("Registro exitoso", 200, ResponseType.SUCCESS, token, darkMode);
    }
}
