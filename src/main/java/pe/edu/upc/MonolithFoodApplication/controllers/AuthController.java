package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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
    // ? Atributos
    // Inyección de dependencias
    private final AuthService authService;
    private final OAuthService oAuthService;
    private final JwtService jwtService;

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
        ResponseDTO response = authService.register(request);
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
        // En el frontent, hacer una solicitud a /auth/set-ip-address(username, ipAddress).
    }
    // Post: Guardar la IP del usuario
    @PostMapping("/set-basic-oauth2-data")
    public ResponseEntity<?> setIpAndWallet(@RequestHeader("Authorization") String bearerToken,
        @RequestParam String ipAddress)
    {
        try { 
            String username = jwtService.getUsernameFromBearerToken(bearerToken);
            ResponseDTO response = oAuthService.setBasicOauth2Data(username, ipAddress);
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
