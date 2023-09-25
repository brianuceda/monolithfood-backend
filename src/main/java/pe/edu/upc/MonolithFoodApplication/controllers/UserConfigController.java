package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userconfig.UserConfigDTO;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserConfigService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/config")
public class UserConfigController {
    // * Atributos
    // Inyección de dependencias
    private final UserConfigService userConfigService;
    private final JwtService jwtService;

    // * Metodos
    @GetMapping("/getConfig")
    public ResponseEntity<?> getConfig(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        UserConfigDTO response = userConfigService.getConfig(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PutMapping("/darkmode")
    public ResponseEntity<?> changeDarkMode(@RequestHeader("Authorization") String bearerToken, @RequestParam Boolean darkMode) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userConfigService.changeDarkMode(username, darkMode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PutMapping("/notifications")
    public ResponseEntity<?> changeNotifications(@RequestHeader("Authorization") String bearerToken, @RequestParam Boolean notifications) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userConfigService.changeNotifications(username, notifications);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
