package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserConfigService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/config")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'VIP')")
@CrossOrigin(origins = { "https://monolithfood.vercel.app" }, allowedHeaders = "**")
public class UserConfigController {
    private final UserConfigService userConfigService;
    private final JwtService jwtService;

    // * Naydeline (Configuración general)
    // Get: Obtener la configuración de un usuario
    @GetMapping
    public ResponseEntity<?> getConfig(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userConfigService.getConfig(username);
        return validateResponse(response);
    }
    // Put: Activar/desactivar modo oscuro
    @PutMapping("/darkmode/update")
    public ResponseEntity<?> changeDarkMode(@RequestHeader("Authorization") String bearerToken,
        @RequestParam Boolean darkMode) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userConfigService.changeDarkMode(username, darkMode);
        return validateResponse(response);
    }
    // Put: Activar/desactivar notificaciones
    @PutMapping("/notifications/update")
    public ResponseEntity<?> changeNotifications(@RequestHeader("Authorization") String bearerToken,
        @RequestParam Boolean notifications) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userConfigService.changeNotifications(username, notifications);
        return validateResponse(response);
    }

    // * Responder a la petición con el código de estado y el mensaje correspondiente
    private ResponseEntity<?> validateResponse(ResponseDTO response) {
        try {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Ocurrio un error", 500, ResponseType.ERROR), HttpStatus.valueOf(500));
        }
    }

}
