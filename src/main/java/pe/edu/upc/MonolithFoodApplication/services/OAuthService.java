package pe.edu.upc.MonolithFoodApplication.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.OAuth2PrincipalDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.RoleRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
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
                if (oa2.getUsername() == null) return new ResponseDTO("No se pudo obtener el nombre de usuario.", 400);
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
                if (oa2.getUsername() == null) return new ResponseDTO("No se pudo obtener el nombre de usuario.", 400);
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
                if (oa2.getUsername() == null) return new ResponseDTO("No se pudo obtener el nombre de usuario.", 400);
                if (oa2.getNames() == null) oa2.setNames(oa2.getUsername().substring(0, 1).toUpperCase() + oa2.getUsername().substring(1));
                if (oa2.getProfileImg() == null) oa2.setProfileImg("https://i.ibb.co/8B1kpZC/microsoft-img.png");
                oa2.setUsername("MS_" + (oa2.getUsername().substring(0, 1).toUpperCase() + (oa2.getUsername().substring(1)).toLowerCase()));
            } else {
                return new ResponseDTO("Proveedor de autenticación aún no configurado.", 400);
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
                .userConfig(uc)
                .roles(setRoleUser())
                .build();
            // Iniciar sesion o registrar
            Optional<UserEntity> oAuthUser = userRepository.findByOauthProviderId(oa2.getOauthProviderId());
            if (oAuthUser.isPresent())
                response = oAuth2Login(oAuthUser.get().getOauthProviderId());
            else if (!oAuthUser.isPresent())
                response = oAuth2Register(user);
            else {
                logger.error("Error al registrar el usuario.");
                return new ResponseDTO("Ocurrió algo inesperado.", 500);
            }
            return response;
        } catch (Exception e) {
            // guardar en el log el error exacto
            logger.error("Entrando al bloque Exception: " + e.getClass().getName());
            logger.error("Causa exacta: " + e.getCause());
            return new ResponseDTO("Ocurrió un error interno.", 500);
        }
    }
    public ResponseDTO oAuth2Login(String oAuthProviderId) {
        UserEntity user = userRepository.findByOauthProviderId(oAuthProviderId).get();
        String generatedToken = jwtService.genToken(user);
        return new AuthResponseDTO("Inicio de sesion realizado correctamente.", HttpStatus.OK.value(), generatedToken, user.getUserConfig().getDarkMode(), false);
    }
    public ResponseDTO oAuth2Register(UserEntity user) {
        userRepository.save(user);
        String generatedToken = jwtService.genToken(user);
        return new AuthResponseDTO("Registro realizado correctamente.", HttpStatus.OK.value(), generatedToken, user.getUserConfig().getDarkMode(), true);
    }

    // ? Funciones auxiliares
    // FUNCIÓN: Devuelve un set con el rol USER para asignarlo a un nuevo usuario
    private Set<RoleEntity> setRoleUser() {
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity USER = roleRepository.findByName(RoleEnum.USER).get();
        roles.add(USER);
        return roles;
    }
}
