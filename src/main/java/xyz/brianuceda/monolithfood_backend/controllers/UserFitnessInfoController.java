package xyz.brianuceda.monolithfood_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xyz.brianuceda.monolithfood_backend.dtos.fitnessinfo.FitnessInfoDTO;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.utils.JwtUtils;
import xyz.brianuceda.monolithfood_backend.services.UserFitnessInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/fitness")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'VIP')")
@CrossOrigin(origins = { "https://monolithfood.vercel.app", "http://localhost:4200" }, allowedHeaders = "**")
public class UserFitnessInfoController {
    private final UserFitnessInfoService userFitnessInfoService;
    private final JwtUtils jwtUtils;

    // * Brian (Objetivos)
    // Get: Obtener todos los objetivos (seleccionando el del usuario)
    @GetMapping("/objectives")
    public ResponseEntity<?> getObjectives(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.getObjectives(username);
        return validateResponse(response);
    }
    // Post: Establecer nuevos objetivos para un usuario
    @PostMapping("/objectives/new")
    public ResponseEntity<?> setObjectives(@RequestHeader("Authorization") String bearerToken,
        @RequestBody List<String> objectives) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.setObjectives(username, objectives);
        return validateResponse(response);
    }
    // Put: Actualizar los objetivos de un usuario
    @PutMapping("/objectives/update")
    public ResponseEntity<?> updateObjectives(@RequestHeader("Authorization") String bearerToken,
        @RequestBody List<String> objectives) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.updateObjectives(username, objectives);
        return validateResponse(response);
    }
    // * Brian (Nivel de actividad física)
    // Get: Obtener todos los niveles de actividad física (seleccionando el del usuario)
    @GetMapping("/activity-levels")
    public ResponseEntity<?> getActivityLevels(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.getActivityLevels(username);
        return validateResponse(response);
    }
    // Post: Establecer nuevo nivel de actividad física para un usuario
    @PostMapping("/activity-levels/new")
    public ResponseEntity<?> setActivityLevel(@RequestHeader("Authorization") String bearerToken,
        @RequestParam String activityLevel) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.setActivityLevel(username, activityLevel);
        return validateResponse(response);
    }
    // Put: Actualizar el nivel de actividad física de un usuario
    @PutMapping("/activity-levels/update")
    public ResponseEntity<?> updateActivityLevel(@RequestHeader("Authorization") String bearerToken,
        @RequestParam String activityLevel) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.updateActivityLevel(username, activityLevel);
        return validateResponse(response);
    }
    // Put: Actualizar información fitness de un usuario
    @PutMapping("/update")
    public ResponseEntity<?>updateFitnessInfo(@RequestHeader("Authorization") String bearerToken,
        @RequestBody FitnessInfoDTO userFitnessInfoDto) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.updateFitnessInfo(username, userFitnessInfoDto);
        return validateResponse(response);
    }
    // Get: Obtener información fitness de un usuario
    @GetMapping("/calc")
    public ResponseEntity<?> calcFitnessInfo(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.calcFitnessInfo(username, false);
        return validateResponse(response);
    }
    // * Reportes
    // Get: Obtener reporte de calorías por día de todos los dias en un ListCaloriasDia
    @GetMapping("/reports/calories")
    public ResponseEntity<?> getCaloriesReport(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.getMacrosReport(username);
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
