package pe.edu.upc.MonolithFoodApplication.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PutHeightWeightDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PutPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.SetInformationDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.services.UserReportService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserPersonalInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mf/user/info")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'VIP')")
@CrossOrigin(origins = { "https://monolithfood.vercel.app", "http://localhost:4200" }, allowedHeaders = "**")
public class UserPersonalInfoController {
    private final UserPersonalInfoService userPersonalInfoService;
    private final JwtService jwtService;
    private final UserReportService basicUserProgressReportService;

    // * Naydeline: Personal Information
    // Post: Registrar información personal de un usuario
    @PostMapping("/new")
    public ResponseEntity<?>setUserPersonalInfo(@RequestHeader("Authorization") String bearerToken,
        @RequestBody SetInformationDTO userPersonallnfoDto) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.setUserPersonalInfo(username, userPersonallnfoDto);
        return validateResponse(response);
    }
    // Get: Obtener información personal de un usuario
    @GetMapping
    public ResponseEntity<?> getInformation(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.getInformation(username);
        return validateResponse(response);
    }
    // Put: Actualizar información personal de un usuario
    @PutMapping("/update")
    public ResponseEntity<?>updatePersonalInfo(@RequestHeader("Authorization") String bearerToken,
        @RequestBody PutPersonalInfoDTO userPersonallnfoDto) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.updateUserPersonalInfo(username, userPersonallnfoDto);
        return validateResponse(response);
    }
    // * Willy (IMC)
    // Get: Actualizar la altura o el peso de un usuario y obtener su IMC
    @PutMapping("/update-weight-height")
    public ResponseEntity<?> updateHeightAndGetIMC(@RequestHeader("Authorization") String bearerToken,
        @RequestBody PutHeightWeightDTO uhwDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.updateHeightWeightGetIMC(username, uhwDTO);
        return validateResponse(response);
    }
    // * Willy (Reportes)
    // Get: Obtener el consumo de calorías de la última semana
    @GetMapping("/last-week-calories")
    public ResponseEntity<?> getCaloriesConsumedInTheLastWeek(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = basicUserProgressReportService.getCaloriesConsumedInTheLastWeek(username);
        return validateResponse(response);
    }
    // Get: Obtener el promedio de consumo de calorías diario
    @GetMapping("/average-calories")
    public ResponseEntity<?> getAverageDailyCaloriesConsumedDTO(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = basicUserProgressReportService.getAverageDailyCaloriesConsumedDTO(username);
        return validateResponse(response);
    }

    //GET: obtener el progreso de peso
    @GetMapping("/progress-weight")
    public ResponseEntity<?> getProgressWeight(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userPersonalInfoService.progressWeight(username);
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
