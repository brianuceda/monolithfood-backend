package pe.edu.upc.MonolithFoodApplication.controllers;


import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
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

    @GetMapping("/foods")
    public List<FoodEntity> searchFoodByName(@RequestParam String name) {
        return foodService.searchFoodByName(name);
    }

    @GetMapping("/foods/byCategory")
    public List<FoodEntity> searchFoodByCategory(@RequestParam String category) {
        return foodService.searchFoodByCategory(category);
    }

    @GetMapping("/foods/byNutrient")
    public List<FoodEntity> searchFoodByNutrient(@RequestParam String nutrient) {
        return foodService.searchFoodByNutrient(nutrient);
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchFood(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "nutrient", required = false) String nutrient) {

        // Call the service to search for foods
        String searchResultMessage = foodService.searchFoods(name, category, nutrient);

        return new ResponseEntity<>(searchResultMessage, HttpStatus.OK);
    }
    
   
}
