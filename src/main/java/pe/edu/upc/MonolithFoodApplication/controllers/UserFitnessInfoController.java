package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserFitnessInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/fitness")
public class UserFitnessInfoController {
    // ? Atributos
    // Inyección de dependencias
    private final UserFitnessInfoService userFitnessInfoService;
    private final JwtService jwtService;

    // ? Metodos
    // * Willy (Información fitness)
    @GetMapping
    public ResponseEntity<?> getFitnessInfo(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.getFitnessInfo(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @GetMapping("/calc")
    public ResponseEntity<?> calcFitnessInfo(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userFitnessInfoService.calcFitnessInfo(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    
}
