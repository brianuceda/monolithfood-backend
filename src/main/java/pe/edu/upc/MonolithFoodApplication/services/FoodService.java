package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;

import java.util.List;


@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<FoodEntity> searchFoodByName(String name) {
      
        return foodRepository.findByNameContaining(name);
    }

    public List<FoodEntity> searchFoodByCategory(String category) {
        return foodRepository.findByCategoryName(category);
    }

    public List<FoodEntity> searchFoodByNutrient(String nutrient) {
        return foodRepository.findByNutrient(nutrient);
    }
    
    public String searchFoods(String name, String category, String nutrient) {
        boolean found = false;

   
        if (!foodRepository.findByNameContaining(name).isEmpty()) {
            found = true;
        } else if (!foodRepository.findByCategoryName(category).isEmpty()) {
            found = true;
        } else if (!foodRepository.findByNutrient(nutrient).isEmpty()) {
            found = true;
        }

        return found ? "Búsqueda exitosa. Se encontraron alimentos." : "No se encontraron resultados.";
    }
}

