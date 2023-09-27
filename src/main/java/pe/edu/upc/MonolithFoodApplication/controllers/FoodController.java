package pe.edu.upc.MonolithFoodApplication.controllers;


import pe.edu.upc.MonolithFoodApplication.dto.FoodNutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dto.SearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.services.FoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/searchByName")
    public ResponseEntity<?> searchFoodsByName(@RequestParam String foodName) {
        try {
            List<SearchFoodDTO> foundFoods = foodService.searchFoodsByName(foodName);
            if (foundFoods.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found by name");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(foundFoods);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }
    

    @GetMapping("/searchByCategory")
    public ResponseEntity<?> searchFoodsByCategory(@RequestParam String categoryName) {
        List<SearchFoodDTO> foundFoods = foodService.searchFoodsByCategory(categoryName);
    
        if (foundFoods.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found by category");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(foundFoods);
        }
    }
    

    @GetMapping("/searchByNutrient")
    public ResponseEntity<?> searchFoodsByNutrient(@RequestParam String nutrientName) {
        List<FoodNutrientDTO> foundFoods = foodService.searchFoodsByNutrient(nutrientName);
    
        if (foundFoods.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found by nutrient");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(foundFoods);
        }
    }
    

    // @GetMapping("/search")
    // public ResponseEntity<String> searchFood(
    //         @RequestParam(name = "name", required = false) String name,
    //         @RequestParam(name = "category", required = false) String category,
    //         @RequestParam(name = "nutrient", required = false) String nutrient) {

    //     // Call the service to search for foods
    //     String searchResultMessage = foodService.searchFoods(name, category, nutrient);

    //     return new ResponseEntity<>(searchResultMessage, HttpStatus.OK);
    // }
    
   
}
