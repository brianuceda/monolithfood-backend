package pe.edu.upc.MonolithFoodApplication.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import pe.edu.upc.MonolithFoodApplication.entities.CategoryIntakeEnum;
import pe.edu.upc.MonolithFoodApplication.services.AuthService;
import pe.edu.upc.MonolithFoodApplication.services.EatService;
import pe.edu.upc.MonolithFoodApplication.services.FavoriteService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    private final EatService eatService;
    private final FavoriteService favoriteService;
    private final JwtService jwtService;

    // * Brian (Auth)
    // Post: Cerrar sesión
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String realToken = jwtService.getRealToken(token);
        ResponseDTO response = authService.logout(realToken);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Naydeline (Información general)
    // Get: Obtener información general de un usuario
    @GetMapping
    public ResponseEntity<?> getGeneralInformation(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.getGeneralInformation(username);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Put: Actualizar la foto de perfil de un usuario
    @PutMapping("/general-info/photo")
    public ResponseEntity<ResponseDTO>updatePhoto(@RequestHeader("Authorization") String bearerToken,
            @RequestParam String photoUrl) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.updatePhoto(username, photoUrl);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Heather (Alimentos consumidos)
    @GetMapping("/intakes/category/all")
    public ResponseEntity<?> getAllCategoryMacrosAndIntakes(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam(required = false) LocalDateTime startDate, 
            @RequestParam(required = false) LocalDateTime endDate
        ) {
        // Si no se mandan fechas, se obtienen todos los alimentos consumidos entre el inicio del día de hoy y el final del día de hoy
        if (startDate == null || endDate == null) {
            LocalDate today = LocalDate.now();
            startDate = today.atStartOfDay();
            endDate = today.atTime(23, 59, 59, 999_000_000);
        }
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.getAllMacrosAndIntakes(username, startDate, endDate);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    @GetMapping("/intakes/category")
    public ResponseEntity<?> getCatMacrosAndIntakes(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam(required = true) CategoryIntakeEnum category,
            @RequestParam(required = false) LocalDateTime startDate, 
            @RequestParam(required = false) LocalDateTime endDate
        ) {
        // Si no se mandan fechas, se obtienen todos los alimentos consumidos entre el inicio del día de hoy y el final del día de hoy
        if (startDate == null || endDate == null) {
            LocalDate today = LocalDate.now();
            startDate = today.atStartOfDay();
            endDate = today.atTime(23, 59, 59, 999_000_000);
        }
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.getCatMacrosAndIntakes(username, category, startDate, endDate, true);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    @GetMapping("/intakes/macros")
    public ResponseEntity<?> getMacrosDetailed(
        @RequestHeader("Authorization") String bearerToken, 
        @RequestParam(required = false) LocalDateTime startDate, 
        @RequestParam(required = false) LocalDateTime endDate
    ) {
        // Si no se mandan fechas, se obtienen todos los alimentos consumidos entre el inicio del día de hoy y el final del día de hoy
        if (startDate == null || endDate == null) {
            LocalDate today = LocalDate.now();
            startDate = today.atStartOfDay();
            endDate = today.atTime(23, 59, 59, 999_000_000);
        }
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.getMacrosDetailed(username, startDate, endDate, true);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    @PostMapping("/intakes/add")
    public ResponseEntity<?> addFoodIntake(@RequestHeader("Authorization") String bearerToken,
            @RequestBody NewIntakeDTO foodIntakeDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.addFoodIntake(username, foodIntakeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @PutMapping("/intakes/update")
    public ResponseEntity<?> updateFoodIntake(@RequestHeader("Authorization") String bearerToken,
            @RequestBody UpdateIntakeDTO newFoodIntakeDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.updateFoodIntake(username, newFoodIntakeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    @DeleteMapping("/intakes/delete")
    public ResponseEntity<?> deleteFoodIntake(@RequestHeader("Authorization") String bearerToken,
            @RequestParam Long foodId) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.deleteFoodIntake(username, foodId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // * Brian (Favoritos)
    @GetMapping("/favorites")
    public ResponseEntity<?> getAllFavoriteFoods(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = favoriteService.getAllFavoriteFoods(username);
        if (response.getStatusCode() == 200 && response.getMessage() == null && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    @PostMapping("/favorites/add")
    public ResponseEntity<?> addFavoriteFood(@RequestHeader("Authorization") String bearerToken, @RequestParam Long foodId) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = favoriteService.addFavoriteFood(username, foodId);
        if (response.getStatusCode() == 200 && response.getMessage() == null && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    @DeleteMapping("/favorites/delete")
    public ResponseEntity<?> deleteFavoriteFood(@RequestHeader("Authorization") String bearerToken, @RequestParam Long foodId) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = favoriteService.deleteFavoriteFood(username, foodId);
        if (response.getStatusCode() == 200 && response.getMessage() == null && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }

}
