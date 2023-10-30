package pe.edu.upc.MonolithFoodApplication.controllers;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/foods")
public class FoodController {
    private final FoodService foodService;

    // * Gabriela (Búsqueda)
    // Get: Buscar todos los alimentos
    @GetMapping
    public ResponseEntity<?> getAllFoods() {
        ResponseDTO response = foodService.getAllFoods();
        return validateResponse(response);
    }
    // * Gabriela (Filtros de búsqueda)
    // Get: Buscar todos los alimentos por nombre
    @GetMapping("/search-by-food-name")
    public ResponseEntity<?> searchFoodsByName(@RequestParam String foodName) {
        ResponseDTO response = foodService.searchFoodsByName(foodName);
        return validateResponse(response);
    }
    // Get: Buscar todos los alimentos por categoría
    @GetMapping("/search-by-category-name")
    public ResponseEntity<?> searchFoodsByCategory(@RequestParam String categoryName) {
        ResponseDTO response = foodService.searchFoodsByCategory(categoryName);
        return validateResponse(response);
    }
    // Get: Buscar todos los alimentos por nutrientes
    @GetMapping("/search-by-nutrient-name")
    public ResponseEntity<?> searchFoodsByNutrient(@RequestParam String nutrientName) {
        ResponseDTO response = foodService.searchFoodsByNutrient(nutrientName);
        return validateResponse(response);
    }
    // Get: Obtener toda la información de un alimento a partir de su id
    @GetMapping("/search/{id}")
    public ResponseEntity<?> getDetailedFoodById(@PathVariable("id") Long id,
        @RequestParam(required = false, defaultValue = "1") Double quantity) {
        ResponseDTO response = foodService.getDetailedFoodById(id, quantity);
        return validateResponse(response);
    }
    
    // * Responder a la petición con el código de estado y el mensaje correspondiente
    private ResponseEntity<?> validateResponse(ResponseDTO response) {
        try {  
            if (response.getStatusCode() == 200 && response.getMessage() == null) {
                response.setStatusCode(null);
                response.setMessage(null);
                return new ResponseEntity<>(response, HttpStatus.valueOf(200));
            } else {
                return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
            }
        } catch (Exception e) {
                return new ResponseEntity<>("Ocurrió un error.", HttpStatus.valueOf(500));
        }
    }

}
