package pe.edu.upc.MonolithFoodApplication.controllers;

import java.util.List;

import pe.edu.upc.MonolithFoodApplication.dtos.searches.FoodNutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.SearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.services.FoodService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class FoodController {
    // * Atributos
    // Inyección de dependencias
    private final FoodService foodService;

    // * Metodos
    // * Search Food
    // Get: Buscar todos los alimentos por nombre
    @GetMapping("/searchByName")
    public ResponseEntity<?> searchFoodsByName(@RequestParam String foodName) {
        try {
            List<SearchFoodDTO> foundFoods = foodService.searchFoodsByName(foodName);
            if (foundFoods.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún alimento con ese nombre");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(foundFoods);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar alimentos por nombre");
        }
    }
    // Get: Buscar todos los alimentos por categoría
    @GetMapping("/searchByCategory")
    public ResponseEntity<?> searchFoodsByCategory(@RequestParam String categoryName) {
        try {
            List<SearchFoodDTO> foundFoods = foodService.searchFoodsByCategory(categoryName);
            if (foundFoods.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún alimento con esa categoría");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(foundFoods);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar alimentos por categoría");
        }
    }
    // Get: Buscar todos los alimentos por nutrientes
    @GetMapping("/searchByNutrient")
    public ResponseEntity<?> searchFoodsByNutrient(@RequestParam String nutrientName) {
        try {
            List<FoodNutrientDTO> foundFoods = foodService.searchFoodsByNutrient(nutrientName);
            if (foundFoods.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún alimento con ese nutriente");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(foundFoods);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar alimentos por nutrientes");
        }
    }

}
