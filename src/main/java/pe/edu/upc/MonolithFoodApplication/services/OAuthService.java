package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.OAuth2PrincipalDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.external.WalletDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.WalletEntity;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class OAuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthService authService;
    private final ExternalApisService externalApisService;
    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);
    
    @Transactional
    public ResponseDTO joinOAuth2(OAuth2AuthenticationToken authentication) {
        try {
            ResponseDTO response = new ResponseDTO();
            // Obtener los datos de autenticacion
            String provider = authentication.getAuthorizedClientRegistrationId();
            OAuth2User principal = authentication.getPrincipal();
            // Acceder a los atributos
            OAuth2PrincipalDTO oa2 = new OAuth2PrincipalDTO();
            // Obtener los datos dependiendo del proveedor
            if ("google".equals(provider)) {
                // Atributos obtenidos de Google
                oa2.setUsername(principal.getAttribute("given_name"));
                oa2.setPassword(null);
                oa2.setEmail(principal.getAttribute("email"));
                oa2.setNames(principal.getAttribute("name"));
                oa2.setProfileImg(principal.getAttribute("picture"));
                oa2.setOauthProviderId(authentication.getName());
                oa2.setIsOauthRegistered(true);
                // Restricciones
                if (oa2.getUsername() == null)
                    return new ResponseDTO("Error durante la autenticacion", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
                if (oa2.getNames() == null) oa2.setNames(oa2.getUsername().substring(0, 1).toUpperCase() + oa2.getUsername().substring(1));
                // if (oa2.getEmail() == null) oa2.setEmail((oa2.getUsername() + "@gmail.com").substring(0).toLowerCase());
                if (oa2.getProfileImg() == null) oa2.setProfileImg("https://i.ibb.co/c1vgK6T/google-img.png");
                oa2.setUsername("GG_" + (oa2.getUsername().substring(0, 1).toUpperCase() + (oa2.getUsername().substring(1)).toLowerCase()));
            } else if ("github".equals(provider)) {
                // Atributos obtenidos de GitHub
                oa2.setUsername(principal.getAttribute("login"));
                oa2.setPassword(null);
                oa2.setEmail(principal.getAttribute("email"));
                oa2.setNames(principal.getAttribute("name"));
                oa2.setProfileImg(principal.getAttribute("avatar_url"));
                oa2.setOauthProviderId(authentication.getName());
                oa2.setIsOauthRegistered(true);
                // Restricciones
                if (oa2.getUsername() == null)
                    return new ResponseDTO("Error durante la autenticacion", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
                if (oa2.getNames() == null) oa2.setNames(oa2.getUsername().substring(0, 1).toUpperCase() + oa2.getUsername().substring(1));
                // if (oa2.getEmail() == null) oa2.setEmail((oa2.getUsername() + "@hotmail.com").substring(0).toLowerCase());
                if (oa2.getProfileImg() == null) oa2.setProfileImg("https://i.ibb.co/vvBKFjR/github-img.png");
                oa2.setUsername("GH_" + (oa2.getUsername().substring(0, 1).toUpperCase() + (oa2.getUsername().substring(1)).toLowerCase()));
            } else if ("microsoft".equals(provider)) {
                // Atributos obtenidos de Microsoft
                String username = principal.getAttribute("displayName");
                if (username != null && !username.isEmpty())
                    oa2.setUsername(username.replaceAll("\\s", ""));
                oa2.setPassword(null);
                oa2.setEmail(principal.getAttribute("mail"));
                oa2.setNames(principal.getAttribute("displayName"));
                oa2.setProfileImg(principal.getAttribute("picture"));
                oa2.setOauthProviderId(authentication.getName());
                oa2.setIsOauthRegistered(true);
                // Restricciones
                if (oa2.getUsername() == null)
                    return new ResponseDTO("Error durante la autenticacion", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
                if (oa2.getNames() == null) oa2.setNames(oa2.getUsername().substring(0, 1).toUpperCase() + oa2.getUsername().substring(1));
                if (oa2.getProfileImg() == null) oa2.setProfileImg("https://i.ibb.co/8B1kpZC/microsoft-img.png");
                oa2.setUsername("MS_" + (oa2.getUsername().substring(0, 1).toUpperCase() + (oa2.getUsername().substring(1)).toLowerCase()));
            } else {
                return new ResponseDTO("El proveedor no es válido", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
            }
            UserConfigEntity uc = UserConfigEntity.builder()
                .notifications(false)
                .darkMode(true)
                .lastFoodEntry(null)
                .lastWeightUpdate(null)
                .build();
            // Crear el usuario con los datos obtenidos
            UserEntity user = UserEntity.builder()
                .username(oa2.getUsername())
                .password(null)
                .email(oa2.getEmail())
                .names(oa2.getNames())
                .profileImg(oa2.getProfileImg())
                .oauthProviderId(oa2.getOauthProviderId())
                .isOauthRegistered(oa2.getIsOauthRegistered())
                .isAccountBlocked(false)
                .ipAddress(null)
                .userConfig(uc)
                .roles(authService.setRoleUser())
                .build();
            // Iniciar sesion o registrar
            Optional<UserEntity> oAuthUser = userRepository.findByOauthProviderId(oa2.getOauthProviderId());
            if (oAuthUser.isPresent())
                response = oAuth2Login(oAuthUser.get().getOauthProviderId());
            else if (!oAuthUser.isPresent())
                response = oAuth2Register(user);
            else {
                return new ResponseDTO("Ocurrió un error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseType.ERROR);
            }
            return response;
        } catch (Exception e) {
            logger.error("Entrando al bloque Exception: " + e.getClass().getName());
            logger.error("Causa exacta: " + e.getCause());
            return new ResponseDTO("Ocurrió un error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseType.ERROR);
        }
    }
    public ResponseDTO oAuth2Login(String oAuthProviderId) {
        UserEntity user = userRepository.findByOauthProviderId(oAuthProviderId).get();
        String profileStage = jwtService.determineProfileStage(user);
        String generatedToken = jwtService.genToken(user, profileStage);
        return new AuthResponseDTO("Inicio de sesión exitoso", HttpStatus.OK.value(), ResponseType.SUCCESS, generatedToken, user.getUserConfig().getDarkMode());
    }
    public ResponseDTO oAuth2Register(UserEntity user) {
        userRepository.save(user);
        String profileStage = "information";
        String generatedToken = jwtService.genToken(user, profileStage);
        return new AuthResponseDTO("Registro exitoso", HttpStatus.OK.value(), ResponseType.SUCCESS, generatedToken, user.getUserConfig().getDarkMode());
    }
    
    @Transactional
    public ResponseDTO setBasicOauth2Data(String username) {
        String ipAddress = AuthService.getClientIp();
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        UserEntity user = optUser.get();
        if (user.getIpAddress() != null)
            return new ResponseDTO("Ya tienes una IP registrada", HttpStatus.OK.value(), ResponseType.INFO);
        // Guarda la ip
        user.setIpAddress(ipAddress);
        // Genera una nueva billetera para el usuario
        WalletDTO walletDTO = externalApisService.getWalletFromIp(ipAddress);
        WalletEntity wallet = WalletEntity.builder()
            .balance(0.0)
            .currency(walletDTO.getCurrency())
            .currencySymbol(walletDTO.getCurrencySymbol())
            .currencyName(walletDTO.getCurrencyName())
            .build();
        user.setWallet(wallet);
        userRepository.save(user);
        return new ResponseDTO(null, 200, null);
    }

}
