package pe.edu.upc.MonolithFoodApplication.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.AverageDailyCaloriesConsumedDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.CaloriesConsumedLastWeekDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoRequestDTO;
import pe.edu.upc.MonolithFoodApplication.services.BasicUserProgressReportService;
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
    private final BasicUserProgressReportService basicUserProgressReportService;

    // ? Metodos
    // * Naydeline: Personal Information
    @GetMapping
    public ResponseEntity<?> getInformation(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.getInformation(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO>updatePersonalInfo(@RequestHeader("Authorization") String bearerToken, @RequestBody PersonalInfoRequestDTO userPersonallnfoDto) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.updateUserPeronalInfo(username, userPersonallnfoDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Willy (IMC)
    @PutMapping("/height")
    public ResponseEntity<?> updateHeightAndGetIMC(@RequestHeader("Authorization") String bearerToken, @RequestParam Double heightCm) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.updateHeightAndGetIMC(username, heightCm);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PutMapping("/weight")
    public ResponseEntity<?> updateWeightAndGetIMC(@RequestHeader("Authorization") String bearerToken, @RequestParam Double weightKg) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.updateWeightAndGetIMC(username, weightKg);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Willy (Reportes)
    @GetMapping("/lastWeekCalories")
    public ResponseEntity<CaloriesConsumedLastWeekDTO> getCaloriesConsumedInTheLastWeek(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        CaloriesConsumedLastWeekDTO caloriesConsumedLastWeekDTO = basicUserProgressReportService.getCaloriesConsumedInTheLastWeek(username);
        return new ResponseEntity<>(caloriesConsumedLastWeekDTO, HttpStatus.OK);
    }
    @GetMapping("/averageCalories")
    public ResponseEntity<?> getAverageDailyCaloriesConsumedDTO(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        List<AverageDailyCaloriesConsumedDTO> averageDailyCaloriesConsumedDTO = basicUserProgressReportService.getAverageDailyCaloriesConsumedDTO(username);
        return new ResponseEntity<>(averageDailyCaloriesConsumedDTO, HttpStatus.OK);
    }

}
