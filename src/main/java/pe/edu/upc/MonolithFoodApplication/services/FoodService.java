package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.dto.FoodCompositionDTO;
import pe.edu.upc.MonolithFoodApplication.dto.FoodNutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dto.SearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.CategoryRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<SearchFoodDTO> searchFoodsByName(String name) {
        List<FoodEntity> foodEntities = foodRepository.findByNameContaining(name);
        return foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
    }

    public List<SearchFoodDTO> searchFoodsByCategory(String name) {
        // Se implementa esta logica porque siempre se está intentado acceder a una lista (getFoods()) de una categoría.
        // Si la BD retorna null en category (no encuentra ninguna category con ese nombre), todos sus atributos serán null
        // Por lo que al intentar acceder a la lista de alimentos (getFoods()) se producirá un NullPointerException
        CategoryFoodEntity category = categoryRepository.findByName(name);
        // Comprueba si la categoría es null (si no encontró resultados)
        if (category == null) {
            return Collections.emptyList(); // Devuelve una lista vacía
        }
        List<FoodEntity> foodEntities = category.getFoods();
        return foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
    }

    public List<FoodNutrientDTO> searchFoodsByNutrient(String nutrient) {
        List<FoodEntity> foodEntities = foodRepository.findByNutrientName(nutrient);
        return foodEntities.stream()
            .map(food -> convertToFoodNutrientDTO(food, nutrient))
            .collect(Collectors.toList());
    }
    
    // Este método convierte un FoodEntity a un SearchFoodDTO
    private SearchFoodDTO convertToSearchFoodDTO(FoodEntity foodEntity) {
        List<FoodCompositionDTO> compositions = foodEntity.getCompositions().stream()
            .map(c -> new FoodCompositionDTO(c.getNutrient().getName(), c.getNutrientQuantity()))
            .collect(Collectors.toList());

        return SearchFoodDTO.builder()
            .foodName(foodEntity.getName())
            .composition(compositions)
            .build();
    }

    // Este método convierte un FoodEntity a un FoodNutrientDTO
    private FoodNutrientDTO convertToFoodNutrientDTO(FoodEntity foodEntity, String nutrientName) {
        return foodEntity.getCompositions().stream()
            .filter(c -> c.getNutrient().getName().equals(nutrientName))
            .map(c -> new FoodNutrientDTO(foodEntity.getName(), c.getNutrientQuantity()))
            .findFirst()
            .orElse(null); // o lanzar una excepción si es apropiado
    }
    
}

