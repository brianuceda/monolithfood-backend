package pe.edu.upc.MonolithFoodApplication.controllers;

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
import pe.edu.upc.MonolithFoodApplication.dtos.user.PutHeightWeightDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PutPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.SetPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserReportService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserPersonalInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/info")
public class UserPersonalInfoController {
    // ? Atributos
    // Inyección de dependencias
    private final UserPersonalInfoService userPersonalInfoService;
    private final JwtService jwtService;
    private final UserReportService basicUserProgressReportService;

    // ? Metodos
    // * Naydeline: Personal Information
    // Post: Registrar información personal de un usuario
    @PostMapping("/new")
    public ResponseEntity<ResponseDTO>setUserPersonalInfo(@RequestHeader("Authorization") String bearerToken, @RequestBody SetPersonalInfoDTO userPersonallnfoDto) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.setUserPersonalInfo(username, userPersonallnfoDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Get: Obtener información personal de un usuario
    @GetMapping
    public ResponseEntity<?> getInformation(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.getInformation(username);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Put: Actualizar información personal de un usuario
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO>updatePersonalInfo(@RequestHeader("Authorization") String bearerToken, @RequestBody PutPersonalInfoDTO userPersonallnfoDto) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.updateUserPersonalInfo(username, userPersonallnfoDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Willy (IMC)
    // Get: Actualizar la altura o el peso de un usuario y obtener su IMC
    @PutMapping("/update-weight-height")
    public ResponseEntity<?> updateHeightAndGetIMC(@RequestHeader("Authorization") String bearerToken, @RequestBody PutHeightWeightDTO uhwDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.updateHeightWeightGetIMC(username, uhwDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Willy (Reportes)
    // Get: Obtener el consumo de calorías de la última semana
    @GetMapping("/last-week-calories")
    public ResponseEntity<?> getCaloriesConsumedInTheLastWeek(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = basicUserProgressReportService.getCaloriesConsumedInTheLastWeek(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // Get: Obtener el promedio de consumo de calorías diario
    @GetMapping("/average-calories")
    public ResponseEntity<?> getAverageDailyCaloriesConsumedDTO(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = basicUserProgressReportService.getAverageDailyCaloriesConsumedDTO(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
