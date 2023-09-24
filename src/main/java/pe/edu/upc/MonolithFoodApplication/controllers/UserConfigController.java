package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userconfig.UserConfigDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserConfigService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/config")
public class UserConfigController {
    // * Atributos
    // Inyección de dependencias
    private final UserConfigService userConfigService;

    // * Metodos
    @GetMapping("/getconfig")
    public ResponseEntity<?> getConfig(@RequestParam String username) {
        UserConfigDTO response = userConfigService.getConfig(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PostMapping("/darkmode")
    public ResponseEntity<?> changeDarkMode(@RequestParam String username, @RequestParam Boolean darkMode) {
        ResponseDTO response = userConfigService.changeDarkMode(username, darkMode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PostMapping("/notifications")
    public ResponseEntity<?> changeNotifications(@RequestParam String username, @RequestParam Boolean notifications) {
        ResponseDTO response = userConfigService.changeNotifications(username, notifications);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
