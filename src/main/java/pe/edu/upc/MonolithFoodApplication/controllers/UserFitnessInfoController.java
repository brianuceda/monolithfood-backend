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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.FitnessInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserFitnessInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/fitness")
public class UserFitnessInfoController {
    private final UserFitnessInfoService userFitnessInfoService;
    private final JwtService jwtService;

    // * Brian (Objetivos)
    // Get: Obtener todos los objetivos (seleccionando el del usuario)
    @GetMapping("/objectives")
    public ResponseEntity<?> getObjectives(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.getObjectives(username);
        if (response.getStatusCode() == 200) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Put: Actualizar los objetivos de un usuario
    @PutMapping("/objectives/select")
    public ResponseEntity<?> selectObjectives(@RequestHeader("Authorization") String bearerToken,
            @RequestBody List<String> objectives) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.selectObjectives(username, objectives);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Brian (Nivel de actividad física)
    // Get: Obtener todos los niveles de actividad física (seleccionando el del usuario)
    @GetMapping("/activity-levels")
    public ResponseEntity<?> getActivityLevels(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.getActivityLevels(username);
        if (response.getStatusCode() == 200) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Put: Actualizar el nivel de actividad física de un usuario
    @PutMapping("/activity-levels/select")
    public ResponseEntity<?> selectActivityLevel(@RequestHeader("Authorization") String bearerToken,
            @RequestParam String activityLevel) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.selectActivityLevel(username, activityLevel);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Willy (Información fitness)
    // Post: Establecer nueva información fitness para un usuario
    @PostMapping("/new")
    public ResponseEntity<ResponseDTO>setUserFitnessInfo(@RequestHeader("Authorization") String bearerToken, @RequestBody FitnessInfoDTO userFitnessInfoDto) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.setUserFitnessInfo(username, userFitnessInfoDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Put: Actualizar información fitness de un usuario
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO>updateUserFitnessInfo(@RequestHeader("Authorization") String bearerToken, @RequestBody FitnessInfoDTO userFitnessInfoDto) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.updateUserFitnessInfo(username, userFitnessInfoDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Get: Obtener información fitness de un usuario
    @GetMapping("/calc")
    public ResponseEntity<?> calcFitnessInfo(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.calcFitnessInfo(username);
        if (response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // * Willy (Macronutrientes consumidos hoy)
    // Get: Obtener el consumo de calorias, protes, carbos, grasas de hoy
    @GetMapping("/today-macronutrients")
    public ResponseEntity<?> getTodayMacronutrients(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.getMacrosConsumedToday(username);
        if (response.getStatusCode() == 200) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
}
