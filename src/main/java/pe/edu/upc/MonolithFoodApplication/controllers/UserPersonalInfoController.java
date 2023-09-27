package pe.edu.upc.MonolithFoodApplication.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.AverageDailyCaloriesConsumedDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.CaloriesConsumedLastWeekDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoRequestDTO;
import pe.edu.upc.MonolithFoodApplication.services.BasicUserProgressReportService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserPersonalInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/info")
public class UserPersonalInfoController {
    // * Atributos
    // Inyección de dependencias
    private final UserPersonalInfoService userPersonalInfoService;
    private final JwtService jwtService;
    private final BasicUserProgressReportService basicUserProgressReportService;

    // * Metodos
    @GetMapping("/all")
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
    @PutMapping("/weight/update")
    public ResponseEntity<IMCDTO> updateWeightAndGetIMC(@RequestParam String username, @RequestParam Double weight) {
        IMCDTO imcDTO = userPersonalInfoService.updateWeightAndGetIMC(username, weight);
        return new ResponseEntity<>(imcDTO, HttpStatus.OK);
    }

    @PutMapping("/height/update")
    public ResponseEntity<IMCDTO> updateHeightAndGetIMC(@RequestParam String username,@RequestParam Double height) {
        IMCDTO imcDTO = userPersonalInfoService.updateHeightAndGetIMC(username, height);
        return new ResponseEntity<>(imcDTO, HttpStatus.OK);
    }
    //calories consumed in the last week
    @GetMapping("/lastWeekCalories")
    public ResponseEntity<CaloriesConsumedLastWeekDTO> getCaloriesConsumedInTheLastWeek(@RequestParam String username) {
        CaloriesConsumedLastWeekDTO caloriesConsumedLastWeekDTO = basicUserProgressReportService.getCaloriesConsumedInTheLastWeek(username);
        return new ResponseEntity<>(caloriesConsumedLastWeekDTO, HttpStatus.OK);
    }
    //average calories consumed per day in the last week
    @GetMapping("/averageCalories")
    public ResponseEntity<?> getAverageDailyCaloriesConsumedDTO(@RequestParam String username) {
        List<AverageDailyCaloriesConsumedDTO> averageDailyCaloriesConsumedDTO = basicUserProgressReportService.getAverageDailyCaloriesConsumedDTO(username);
        return new ResponseEntity<>(averageDailyCaloriesConsumedDTO, HttpStatus.OK);
    }

}
