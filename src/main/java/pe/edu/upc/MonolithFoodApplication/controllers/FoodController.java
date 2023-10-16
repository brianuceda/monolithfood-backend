package pe.edu.upc.MonolithFoodApplication.controllers;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.FoodService;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/foods")
public class FoodController {
    private final JwtService jwtService;
    private final FoodService foodService;

    // * Gabriela (Filtros de búsqueda)
    // Get: Buscar todos los alimentos por nombre
    @GetMapping("/search-by-food-name")
    public ResponseEntity<?> searchFoodsByName(@RequestParam String foodName) {
        ResponseDTO response = foodService.searchFoodsByName(foodName);
        if (response.getStatusCode() == 200) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Get: Buscar todos los alimentos por categoría
    @GetMapping("/search-by-category-name")
    public ResponseEntity<?> searchFoodsByCategory(@RequestParam String categoryName) {
        ResponseDTO response = foodService.searchFoodsByCategory(categoryName);
        if (response.getStatusCode() == 200) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Get: Buscar todos los alimentos por nutrientes
    @GetMapping("/search-by-nutrient-name")
    public ResponseEntity<?> searchFoodsByNutrient(@RequestParam String nutrientName) {
        ResponseDTO response = foodService.searchFoodsByNutrient(nutrientName);
        if (response.getStatusCode() == 200) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    
    // * Brian (Favoritos)
    // Get: Obtener todos los alimentos favoritos de un usuario
    @GetMapping("/favorites")
    public ResponseEntity<?> getAllFavoriteFoods(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = foodService.getAllFavoriteFoods(username);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Post: Agregar un alimento a la lista de los alimentos favoritos de un usuario
    @PostMapping("/favorites/add")
    public ResponseEntity<?> addFavoriteFood(@RequestHeader("Authorization") String bearerToken, @RequestParam Long foodId) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = foodService.addFavoriteFood(username, foodId);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }
    // Delete: Eliminar un alimento de la lista de alimentos favoritos de un usuario
    @DeleteMapping("/favorites/delete")
    public ResponseEntity<?> deleteFavoriteFood(@RequestHeader("Authorization") String bearerToken, @RequestParam Long foodId) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = foodService.deleteFavoriteFood(username, foodId);
        if (response.getStatusCode() == 200 && response.getMessage() == null) {
            response.setStatusCode(null);
            response.setMessage(null);
            return new ResponseEntity<>(response, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        }
    }

}
