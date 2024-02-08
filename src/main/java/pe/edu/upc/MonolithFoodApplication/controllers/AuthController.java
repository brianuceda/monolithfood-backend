package pe.edu.upc.MonolithFoodApplication.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.services.AuthService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.OAuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "**", allowedHeaders = "**")
public class AuthController {
    private final AuthService authService;
    private final OAuthService oAuthService;
    private final JwtService jwtService;
    private final String redirectOauth2Url = "http://localhost:4200/oauth-callback";

    // * Brian (Auth)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        ResponseDTO response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        ResponseDTO response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // * Brian (OAuth2)
    @GetMapping("/oauth2")
    public ResponseEntity<?> authOauth2(OAuth2AuthenticationToken authentication, HttpServletResponse response) throws IOException {
        if (authentication == null) {
            ResponseDTO responseDTO = new ResponseDTO("No hay datos de autenticación.", 400, ResponseType.ERROR);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        } else {
            ResponseDTO responseDTO = oAuthService.joinOAuth2(authentication);
            
            if (responseDTO instanceof AuthResponseDTO) {
                AuthResponseDTO authResponse = (AuthResponseDTO) responseDTO;
                String token = authResponse.getToken();
                String redirectUrl = this.redirectOauth2Url + "?token=" + token;
                response.sendRedirect(redirectUrl);
                return ResponseEntity.ok().build();
            } else {
                // Manejar el caso en que la respuesta no sea una instancia de AuthResponseDTO
                return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping("/set-basic-oauth2-data")
    public ResponseEntity<?> setIpAndWallet(@RequestHeader("Authorization") String bearerToken) {
        try { 
            String username = jwtService.getUsernameFromBearerToken(bearerToken);
            ResponseDTO response = oAuthService.setBasicOauth2Data(username);
            return validateResponse(response);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error", 500, ResponseType.ERROR), HttpStatus.valueOf(500));
        }
    }

    // * Responder a la petición con el código de estado y el mensaje correspondiente
    private ResponseEntity<?> validateResponse(ResponseDTO response) {
        try {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Ocurrió un error", 500, ResponseType.ERROR), HttpStatus.valueOf(500));
        }
    }
}
