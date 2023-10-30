package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
// import pe.edu.upc.MonolithFoodApplication.dtos.searches.FoodNutrientDTO;
// import pe.edu.upc.MonolithFoodApplication.dtos.searches.ListFoodNutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.ListSearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.NutrientDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.SearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.CategoryFoodDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.DetailedFoodDTO;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;
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
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
        // List<SearchFoodDTO> foodNutrientDTOs = foodEntities.stream()
        //     .map(food -> convertToSearchFoodDTO(food, nutrientName))
        //     .collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, searchFoodDTOs);
    }
    // * Gabriela: Buscar todos los detalles de un alimento a partir de su id
    public ResponseDTO getDetailedFoodById(Long id, Double quantity) {
        List<Object[]> dto = foodRepository.findDetailedFood(id);
        if (dto.isEmpty())
            return new ResponseDTO("Alimento no encontrado.", 404);
        List<Object[]> nutrients = foodRepository.findNutrientsOfFood(id, quantity);
        if (nutrients.isEmpty())
            return new ResponseDTO("Alimento no encontrado.", 404);
        // Convierte la tupla a una lista de DTOs
        List<NutrientDTO> nutrientsOfFood = nutrients.stream()
            .map(n -> new NutrientDTO(
                (String) n[0],
                this.round((Double) n[1]),
                (UnitOfMeasurementEnum) n[2],
                (String) n[3]
            ))
            .collect(Collectors.toList());
        // Convierte la tupla a un DTO
        DetailedFoodDTO detailedFoodDTO = new DetailedFoodDTO(
            (Long) dto.get(0)[0],
            (String) dto.get(0)[1],
            (String) dto.get(0)[2],
            (String) dto.get(0)[3],
            (String) dto.get(0)[4],
            new CategoryFoodDTO(
                (String) dto.get(0)[5],
                (String) dto.get(0)[6],
                (String) dto.get(0)[7],
                (String) dto.get(0)[8]
            ), nutrientsOfFood
        );
        detailedFoodDTO.noMessageAndStatusCode();
        return detailedFoodDTO;
    }

    // ? Funciones auxiliares
    // Método para redondear un valor de forma concisa
    public Double round(Double value) {
        if (value == null) return null;
        return Double.valueOf(String.format("%.2f", value));
    }
    // Método para convertir una entidad a un DTO
    private SearchFoodDTO convertToSearchFoodDTO(FoodEntity foodEntity) {
        return SearchFoodDTO.builder()
            .foodId(foodEntity.getId())
            .foodName(foodEntity.getName())
            .information(foodEntity.getInformation())
            .imgUrl(foodEntity.getImgUrl())
            .build();
    }
    // Método para convertir una entidad a un DTO y filtrar por nombre de nutriente
    // private SearchFoodDTO convertToSearchFoodDTO(FoodEntity foodEntity, String nutrientName) {
    //     return foodEntity.getCompositions().stream().filter(c -> c.getNutrient().getName().equals(nutrientName))
    //         .map(c -> new SearchFoodDTO(foodEntity.getId(), foodEntity.getName(), foodEntity.getInformation(), foodEntity.getImgUrl()))
    //         .findFirst()
    //         .orElse(null);
    // }
    
}
