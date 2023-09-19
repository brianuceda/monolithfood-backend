package pe.edu.upc.MonolithFoodApplication.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.LoginRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RegisterRequestDTO;
import pe.edu.upc.MonolithFoodApplication.services.AuthService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "**")
@RequestMapping("/auth")
public class AuthController {

    // Attributes
    @Autowired
    private final AuthService authService;

    // Methods
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            return new ResponseEntity<AuthResponseDTO>(authService.login(request), HttpStatus.OK);
        } catch(Exception e) {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("error", "Invalid username/password supplied");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        try {
            return new ResponseEntity<AuthResponseDTO>(authService.register(request), HttpStatus.OK);
        } catch(Exception e) {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("error", "This username/email has been used");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/showEncondePasswords")
    public ResponseEntity<String> showEncondePasswords() {
        return new ResponseEntity<String>(authService.showEncondePasswords(), HttpStatus.OK);
    }

}
