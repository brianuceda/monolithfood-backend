package pe.edu.upc.MonolithFoodApplication.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.NewIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.UpdateIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.AuthService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    // ? Atributos
    // Inyección de dependencias
    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;

    // ? Metodos
    // * Brian (Auth)
    // Post: Cerrar sesión
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String realToken = jwtService.getRealToken(token);
        ResponseDTO response = authService.logout(realToken);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Naydeline (Información personal)
    // Get: Obtener información personal de un usuario
    @GetMapping
    public ResponseEntity<?> getInformation(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.getInformation(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Put: Actualizar la foto de perfil de un usuario
    @PutMapping("/general-info/photo")
    public ResponseEntity<ResponseDTO>updatePhoto(@RequestHeader("Authorization") String bearerToken,
            @RequestParam String photoUrl) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.updatePhoto(username, photoUrl);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Brian (Objetivos)
    // Get: Obtener todos los objetivos disponibles
    @GetMapping("/objectives/all")
    public ResponseEntity<?> getAllObjectives() {
        ResponseDTO response = userService.getAllObjectives();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Get: Obtener los objetivos de un usuario
    @GetMapping("/objectives")
    public ResponseEntity<?> getMyObjectives(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.getMyObjectives(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Put: Actualizar los objetivos de un usuario
    @PutMapping("/objectives/update")
    public ResponseEntity<?> selectObjectives(@RequestHeader("Authorization") String bearerToken,
            @RequestBody List<String> objectives) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.selectObjectives(username, objectives);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Heather (Alimentos consumidos)
    // Get: Obtener todos los alimentos consumidos por un usuario
    @GetMapping("/intakes/all")
    public ResponseEntity<?> getAllFoodIntake(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.getAllFoodIntake(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Get: Obtener todos los alimentos consumidos por un usuario entre dos fechas
    @GetMapping("/intakes")
    public ResponseEntity<?> getAllFoodIntakeByDate(@RequestHeader("Authorization") String bearerToken, 
            @RequestParam(required = false) LocalDateTime startDate, 
            @RequestParam(required = false) LocalDateTime endDate) {
        // Si no se mandan fechas, se obtienen todos los alimentos consumidos entre el inicio del día de hoy y el final del día de hoy
        if (startDate == null || endDate == null) {
            LocalDate today = LocalDate.now();
            // Establecer la hora de inicio al inicio del día y la hora de finalización al final del día
            startDate = today.atStartOfDay();
            endDate = today.atTime(23, 59, 59, 999_000_000);
        }
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.getAllFoodIntakeBetweenDates(username, startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Post: Agregar un alimento a la lista de alimentos consumidos por un usuario
    @PostMapping("/intakes/add")
    public ResponseEntity<?> addFoodIntake(@RequestHeader("Authorization") String bearerToken,
            @RequestBody NewIntakeDTO foodIntakeDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.addFoodIntake(username, foodIntakeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Put: Actualizar un alimento de la lista de alimentos consumidos por un usuario
    @PutMapping("/intakes/update")
    public ResponseEntity<?> updateFoodIntake(@RequestHeader("Authorization") String bearerToken,
            @RequestBody UpdateIntakeDTO newFoodIntakeDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.updateFoodIntake(username, newFoodIntakeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Delete: Quitar un alimento de la lista de alimentos consumidos por un usuario
    @DeleteMapping("/intakes/delete")
    public ResponseEntity<?> deleteFoodIntake(@RequestHeader("Authorization") String bearerToken,
            @RequestParam Long foodId) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.deleteFoodIntake(username, foodId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
