package pe.edu.upc.MonolithFoodApplication.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.AuthService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    // * Atributos
    // Inyección de dependencias
    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;

    // * Metodos
    // Test
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("Test", HttpStatus.OK);
    }
    // Auth
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String realToken = jwtService.getRealToken(token);
        ResponseDTO response = authService.logout(realToken);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Objectives
    @GetMapping("/getAllObjectives")
    public ResponseEntity<?> getAllObjectives(@RequestHeader("Authorization") String bearerToken) {
        ResponseDTO response = userService.getAllObjectives();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @GetMapping("/getUserObjectives")
    public ResponseEntity<?> getObjectives(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.getUserObjectives(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PutMapping("/setUserObjectives")
    public ResponseEntity<?> setObjectives(@RequestHeader("Authorization") String bearerToken,
            @RequestBody List<String> objectives) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.setUserObjectives(username, objectives);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}