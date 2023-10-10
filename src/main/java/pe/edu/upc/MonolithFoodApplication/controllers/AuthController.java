package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.AuthService;
import pe.edu.upc.MonolithFoodApplication.services.OAuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    // ? Atributos
    // Inyección de dependencias
    private final AuthService authService;
    private final OAuthService oAuthService;

    // ? Metodos
    // * Brian (Auth)
    // Post: Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        ResponseDTO response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Post: Crear cuenta
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        ResponseDTO response = authService.register(request, false);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Brian (OAuth2)
    // Post: Iniciar sesión con un proveedor OAuth2
    @GetMapping("/oauth2")
    public ResponseEntity<?> authOauth2(OAuth2AuthenticationToken authentication) {
        ResponseDTO response = new ResponseDTO();
        if (authentication == null) {
            response.setMessage("No hay datos de autenticación.");
            response.setStatusCode(400);
        } else {
            response = oAuthService.joinOAuth2(authentication);
        }
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
