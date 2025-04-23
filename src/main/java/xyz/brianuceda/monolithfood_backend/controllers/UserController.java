package xyz.brianuceda.monolithfood_backend.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.NewIntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.UpdateIntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.CategoryIntakeEnum;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.services.AuthService;
import xyz.brianuceda.monolithfood_backend.services.EatService;
import xyz.brianuceda.monolithfood_backend.services.FavoriteService;
import xyz.brianuceda.monolithfood_backend.services.UserService;
import xyz.brianuceda.monolithfood_backend.utils.JwtUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'VIP')")
@CrossOrigin(origins = { "https://monolithfood.vercel.app", "http://localhost:4200" }, allowedHeaders = "**")
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    private final EatService eatService;
    private final FavoriteService favoriteService;
    private final JwtUtils jwtUtils;

    // * Brian (Auth)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String realToken = jwtUtils.getRealToken(token);
        ResponseDTO response = authService.logout(realToken);
        return validateResponse(response);
    }
    // * Naydeline (Información general)
    // Get: Obtener información general de un usuario
    @GetMapping
    public ResponseEntity<?> getGeneralInformation(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.getGeneralInformation(username);
        return validateResponse(response);
    }
    // Put: Actualizar la foto de perfil de un usuario
    @PutMapping("/photo")
    public ResponseEntity<?>updatePhoto(@RequestHeader("Authorization") String bearerToken,
        @RequestParam String photoUrl) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = userService.updatePhoto(username, photoUrl);
        return validateResponse(response);
    }
    // * Brian (Macro y micro nutrientes)
    // Get: Obtener los macronutrientes consumidos en un rango de fechas
    @GetMapping("/intakes/macros")
    public ResponseEntity<?> getMacrosDetailed(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam(required = false) LocalDateTime startDate,
        @RequestParam(required = false) LocalDateTime endDate
    ) {
        validateDates(startDate, endDate);
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.getMacrosDetailed(username, startDate, endDate, true);
        return validateResponse(response);
    }
    // Get: Obtener los macronutrientes y alimentos consumidos en una categoría específica en un rango de fechas
    @GetMapping("/intakes/category")
    public ResponseEntity<?> getSpecificMacrosAndIntakes(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam(required = true) CategoryIntakeEnum category,
        @RequestParam(required = false) LocalDateTime startDate,
        @RequestParam(required = false) LocalDateTime endDate
    ) {
        validateDates(startDate, endDate);
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.getSpecificMacrosAndIntakes(username, category, startDate, endDate);
        return validateResponse(response);
    }
    // Get: Obtener los macronutrientes y alimentos consumidos en todas las categorías en un rango de fechas
    @GetMapping("/intakes/category/all")
    public ResponseEntity<?> getAllCategoryMacrosAndIntakes(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam(required = false) LocalDateTime startDate,
        @RequestParam(required = false) LocalDateTime endDate
    ) {
        validateDates(startDate, endDate);
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.getAllMacrosAndIntakes(username, startDate, endDate);

        // Código modificado para ajustarse a la estructura de IntakesResponseDTO

        return validateResponse(response);
    }
    // * Heather (Alimentos consumidos)
    // Get: Obtener los detalles de un alimento consumido
    @GetMapping("/intakes/{id}")
    public ResponseEntity<?> getDetailedIntake(@RequestHeader("Authorization") String bearerToken,
        @PathVariable("id") Long id) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.getDetailedIntake(username, id);
        return validateResponse(response);
    }
    // Post: Agregar un alimento a la lista de alimentos consumidos
    @PostMapping("/intakes/add")
    public ResponseEntity<?> addFoodIntake(@RequestHeader("Authorization") String bearerToken,
        @RequestBody NewIntakeDTO foodIntakeDTO) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.addFoodIntake(username, foodIntakeDTO);
        return validateResponse(response);
    }
    // Put: Actualizar un alimento consumido
    @PutMapping("/intakes/update")
    public ResponseEntity<?> updateFoodIntake(@RequestHeader("Authorization") String bearerToken,
        @RequestBody UpdateIntakeDTO newFoodIntakeDTO) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.updateFoodIntake(username, newFoodIntakeDTO);
        return validateResponse(response);
    }
    // Delete: Eliminar un alimento consumido
    @DeleteMapping("/intakes/delete")
    public ResponseEntity<?> deleteFoodIntake(@RequestHeader("Authorization") String bearerToken,
        @RequestParam Long id) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = eatService.deleteFoodIntake(username, id);
        return validateResponse(response);
    }
    // * Brian (Favoritos)
    @PreAuthorize("hasAnyRole('ADMIN', 'VIP')")
    @GetMapping("/favorites")
    public ResponseEntity<?> getAllFavoriteFoods(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = favoriteService.getAllFavoriteFoods(username);
        return validateResponse(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'VIP')")
    @PostMapping("/favorites/add")
    public ResponseEntity<?> addFavoriteFood(@RequestHeader("Authorization") String bearerToken,
        @RequestParam Long id) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = favoriteService.addFavoriteFood(username, id);
        return validateResponse(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'VIP')")
    @DeleteMapping("/favorites/delete")
    public ResponseEntity<?> deleteFavoriteFood(@RequestHeader("Authorization") String bearerToken,
        @RequestParam Long id) {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = favoriteService.deleteFavoriteFood(username, id);
        return validateResponse(response);
    }

    // ? Funciones
    // Si no se mandan fechas, se obtienen todos los alimentos consumidos entre el inicio del día de hoy y el final del día de hoy
    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            LocalDate today = LocalDate.now();
            startDate = today.atStartOfDay();
            endDate = today.atTime(23, 59, 59, 999_000_000);
        }
    }
    // Responder a la petición con el código de estado y el mensaje correspondiente
    private ResponseEntity<?> validateResponse(ResponseDTO response) {
        try {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Ocurrio un error", 500, ResponseType.ERROR), HttpStatus.valueOf(500));
        }
    }

}
