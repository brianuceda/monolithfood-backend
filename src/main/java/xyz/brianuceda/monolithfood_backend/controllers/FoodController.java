package xyz.brianuceda.monolithfood_backend.controllers;

import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.services.FoodService;
import xyz.brianuceda.monolithfood_backend.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/foods")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'VIP')")
@CrossOrigin(origins = { "https://monolithfood.vercel.app", "http://localhost:4200" }, allowedHeaders = "**")
public class FoodController {
    private final FoodService foodService;
    private final JwtUtils jwtUtils;

    // * Gabriela (Búsqueda)
    // Get: Buscar todos los alimentos
    @GetMapping
    public ResponseEntity<?> getAllFoods(
        @RequestHeader("Authorization") String bearerToken)
    {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = foodService.getAllFoods(username);
        return validateResponse(response);
    }
    // * Gabriela (Filtros de búsqueda)
    // Get: Buscar todos los alimentos por nombre
    @GetMapping("/search-by-food-name")
    public ResponseEntity<?> searchFoodsByName(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam String foodName)
    {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = foodService.searchFoodsByName(username, foodName);
        return validateResponse(response);
    }
    // Get: Buscar todos los alimentos por categoría
    @GetMapping("/search-by-category-name")
    public ResponseEntity<?> searchFoodsByCategory(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam String categoryName)
    {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = foodService.searchFoodsByCategory(username, categoryName);
        return validateResponse(response);
    }
    // Get: Buscar todos los alimentos por nutrientes
    @GetMapping("/search-by-nutrient-name")
    public ResponseEntity<?> searchFoodsByNutrient(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam String nutrientName)
    {
        String username = jwtUtils.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = foodService.searchFoodsByNutrient(username, nutrientName);
        return validateResponse(response);
    }
    // Get: Obtener toda la información de un alimento a partir de su id
    @GetMapping("/search/{id}")
    public ResponseEntity<?> getDetailedFoodById(@PathVariable("id") Long id,
        @RequestParam(required = false, defaultValue = "100") Double quantity) {
        ResponseDTO response = foodService.getDetailedFoodById(id, quantity);
        System.out.println(response);
        return validateResponse(response);
    }
    @GetMapping("search/nutrients/{id}")
    public ResponseEntity<?> getNutrientsByFoodId(@PathVariable("id") Long id,
        @RequestParam(required = false, defaultValue = "100") Double quantity) {
        ResponseDTO response = foodService.getNutrientsByFoodId(id, quantity);
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
