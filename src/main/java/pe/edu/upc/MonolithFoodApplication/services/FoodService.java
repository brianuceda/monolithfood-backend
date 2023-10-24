package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.FoodNutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.ListFoodNutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.ListSearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.SearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.CategoryFoodDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.DetailedFoodDTO;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.CategoryRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    // * Gabriela: Buscar todos los alimentos
    public ResponseDTO getAllFoods() {
        List<FoodEntity> foodEntities = foodRepository.findAllByOrderByIdAsc();
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos", 404);
        }
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, searchFoodDTOs);
    }
    // * Gabriela: Buscar alimentos por nombre
    public ResponseDTO searchFoodsByName(String foodName) {
        List<FoodEntity> foodEntities = foodRepository.findByNameContaining(foodName);
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos con ese nombre", 404);
        }
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, searchFoodDTOs);
    }
    // * Gabriela: Buscar alimentos por categoría
    public ResponseDTO searchFoodsByCategory(String categoryName) {
        CategoryFoodEntity category = categoryRepository.findByName(categoryName);
        if (category == null) {
            return new ResponseDTO("No se encontró la categoría", 404);
        }
        List<FoodEntity> foodEntities = category.getFoods();
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos en esa categoría", 404);
        }
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, searchFoodDTOs);
    }
    // * Gabriela: Buscar alimentos por nutriente
    public ResponseDTO searchFoodsByNutrient(String nutrientName) {
        List<FoodEntity> foodEntities = foodRepository.findByNutrientName(nutrientName);
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos con ese nutriente", 404);
        }
        List<FoodNutrientDTO> foodNutrientDTOs = foodEntities.stream()
            .map(food -> convertToFoodNutrientDTO(food, nutrientName))
            .collect(Collectors.toList());
        return new ListFoodNutrientDTO(null, 200, foodNutrientDTOs);
    }
    // * Gabriela: Buscar todos los detalles de un alimento a partir de su id
    public ResponseDTO getDetailedIntake(Long id) {
        List<Object[]> dto = foodRepository.findDetailedFood(id);
        if (dto.isEmpty())
            return new ResponseDTO("Alimento no encontrado.", 404);
        DetailedFoodDTO detailedFoodDTO = new DetailedFoodDTO(
            (Long) dto.get(0)[0],
            (String) dto.get(0)[1],
            (String) dto.get(0)[2],
            (String) dto.get(0)[3],
            new CategoryFoodDTO(
                (String) dto.get(0)[4],
                (String) dto.get(0)[5],
                (String) dto.get(0)[6],
                (String) dto.get(0)[7]
            )
        );
        detailedFoodDTO.noMessageAndStatusCode();
        return detailedFoodDTO;
    }

    // ? Funciones auxiliares
    private SearchFoodDTO convertToSearchFoodDTO(FoodEntity foodEntity) {
        // List<FoodCompositionDTO> compositions = foodEntity.getCompositions().stream()
        //     .map(c -> new FoodCompositionDTO(c.getNutrient().getName(), c.getNutrientQuantity()))
        //     .collect(Collectors.toList());
        return SearchFoodDTO.builder()
            .foodId(foodEntity.getId())
            .foodName(foodEntity.getName())
            .information(foodEntity.getInformation())
            .imgUrl(foodEntity.getImgUrl())
            // .composition(compositions)
            .build();
    }
    private FoodNutrientDTO convertToFoodNutrientDTO(FoodEntity foodEntity, String nutrientName) {
        return foodEntity.getCompositions().stream()
            .filter(c -> c.getNutrient().getName().equals(nutrientName))
            .map(c -> new FoodNutrientDTO(foodEntity.getId(), foodEntity.getName(), foodEntity.getInformation(), foodEntity.getImgUrl(), c.getNutrientQuantity()))
            .findFirst()
            .orElse(null);
    }
    
}
