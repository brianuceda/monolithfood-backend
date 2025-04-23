package pe.edu.upc.MonolithFoodApplication.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.services.AuthService;
import pe.edu.upc.MonolithFoodApplication.services.OAuthService;

@Log
@RestController
@RequestMapping("/api/v1/mf/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = { "https://monolithfood.vercel.app", "http://localhost:4200" }, allowedHeaders = "**")
public class AuthController {
    private final AuthService authService;
    private final OAuthService oAuthService;
    private final String redirectOauth2Url = "https://monolithfood.vercel.app/oauth-callback";

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
                log.info("No se pudo obtener el token de acceso.");
                return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
