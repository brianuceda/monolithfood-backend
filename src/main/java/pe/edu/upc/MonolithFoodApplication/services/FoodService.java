package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.FoodCompositionDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.FoodNutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.SearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.CategoryRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    // ? Atributos
    // Inyección de dependencias
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    // ? Metodos
    // * Gabriela: Buscar alimentos por nombre
    public List<SearchFoodDTO> searchFoodsByName(String name) {
        List<FoodEntity> foodEntities = foodRepository.findByNameContaining(name);
        // Si no encontró resultados, retorna una lista vacía
        if (foodEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
    }
    // * Gabriela: Buscar alimentos por categoría
    public List<SearchFoodDTO> searchFoodsByCategory(String name) {
        CategoryFoodEntity category = categoryRepository.findByName(name);
        // Si no encontró resultados, retorna una lista vacía
        if (category == null) {
            return Collections.emptyList();
        }
        List<FoodEntity> foodEntities = category.getFoods();
        return foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
    }
    // * Gabriela: Buscar alimentos por nutriente
    public List<FoodNutrientDTO> searchFoodsByNutrient(String nutrient) {
        List<FoodEntity> foodEntities = foodRepository.findByNutrientName(nutrient);
        // Si no encontró resultados, retorna una lista vacía
        if (foodEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return foodEntities.stream()
            .map(food -> convertToFoodNutrientDTO(food, nutrient))
            .collect(Collectors.toList());
    }

    // ? Funciones auxiliares
    // FUNCIÓN: Convierte FoodEntity a SearchFoodDTO
    private SearchFoodDTO convertToSearchFoodDTO(FoodEntity foodEntity) {
        // Convierte la lista de FoodCompositionEntity a una lista de FoodCompositionDTO
        List<FoodCompositionDTO> compositions = foodEntity.getCompositions().stream()
            .map(c -> new FoodCompositionDTO(c.getNutrient().getName(), c.getNutrientQuantity()))
            .collect(Collectors.toList());
        return SearchFoodDTO.builder()
            .foodName(foodEntity.getName())
            .composition(compositions)
            .build();
    }
    // FUNCIÓN: Convierte FoodEntity a FoodNutrientDTO
    private FoodNutrientDTO convertToFoodNutrientDTO(FoodEntity foodEntity, String nutrientName) {
        return foodEntity.getCompositions().stream()
            .filter(c -> c.getNutrient().getName().equals(nutrientName))
            .map(c -> new FoodNutrientDTO(foodEntity.getName(), c.getNutrientQuantity()))
            .findFirst()
            .orElse(null);
    }
    
}
