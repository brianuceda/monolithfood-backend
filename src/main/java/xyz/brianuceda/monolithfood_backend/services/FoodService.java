package xyz.brianuceda.monolithfood_backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.ListSearchFoodDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.NutrientDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.SearchFoodDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.DetailedFoodDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.ListNutrientsDTO;
import xyz.brianuceda.monolithfood_backend.entities.CategoryFoodEntity;
import xyz.brianuceda.monolithfood_backend.entities.FoodEntity;
import xyz.brianuceda.monolithfood_backend.entities.UserEntity;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.enums.UnitOfMeasurementEnum;
import xyz.brianuceda.monolithfood_backend.repositories.CategoryRepository;
import xyz.brianuceda.monolithfood_backend.repositories.FoodRepository;
import xyz.brianuceda.monolithfood_backend.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    
    // * Gabriela: Buscar todos los alimentos
    @Cacheable(value = "foods", key = "'allFoods'")
    public ResponseDTO getAllFoods(String username) {
        List<FoodEntity> foodEntities = foodRepository.findAllByOrderByIdAsc();
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
        }
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(f -> {
            SearchFoodDTO searchFoodDTO = this.convertToSearchFoodDTO(username, f);
            return searchFoodDTO;
        }).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, null, searchFoodDTOs);
    }
    // * Gabriela: Buscar alimentos por nombre
    public ResponseDTO searchFoodsByName(String username, String foodName) {
        List<FoodEntity> foodEntities = foodRepository.findByNameContaining(foodName);
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos con ese nombre", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
        }
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(f -> {
            SearchFoodDTO searchFoodDTO = this.convertToSearchFoodDTO(username, f);
            return searchFoodDTO;
        }).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, null, searchFoodDTOs);
    }
    // * Gabriela: Buscar alimentos por categoría
    public ResponseDTO searchFoodsByCategory(String username, String categoryName) {
        CategoryFoodEntity category = categoryRepository.findByName(categoryName);
        if (category == null) {
            return new ResponseDTO("No se encontró la categoría", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        List<FoodEntity> foodEntities = category.getFoods();
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos en esa categoría", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
        }
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(f -> {
            SearchFoodDTO searchFoodDTO = this.convertToSearchFoodDTO(username, f);
            return searchFoodDTO;
        }).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, null, searchFoodDTOs);
    }
    // * Gabriela: Buscar alimentos por nutriente
    public ResponseDTO searchFoodsByNutrient(String username, String nutrientName) {
        List<FoodEntity> foodEntities = foodRepository.findByNutrientName(nutrientName);
        if (foodEntities.isEmpty()) {
            return new ResponseDTO("No se encontraron alimentos con ese nutriente", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
        }
        List<SearchFoodDTO> searchFoodDTOs = foodEntities.stream().map(f -> {
            SearchFoodDTO searchFoodDTO = this.convertToSearchFoodDTO(username, f);
            return searchFoodDTO;
        }).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, null, searchFoodDTOs);
    }
    // * Gabriela: Buscar todos los detalles de un alimento a partir de su id
    public ResponseDTO getDetailedFoodById(Long id, Double quantity) {
        DetailedFoodDTO dto = foodRepository.findDetailedFood(id);
        if (dto == null)
            return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        dto.setUnitOfMeasurement(UnitOfMeasurementEnum.G);
        dto.setQuantity(quantity);
        List<Object[]> nutrients = foodRepository.findNutrientsOfFood(id);
        if (nutrients.isEmpty())
            return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        // Convierte la tupla a una lista de DTOs
        List<NutrientDTO> nutrientsOfFood = nutrients.stream()
            .map(n -> new NutrientDTO(
                (Long) n[0],
                (String) n[1],
                this.round((Double) n[2]),
                (UnitOfMeasurementEnum) n[3],
                (String) n[4]
            ))
            .collect(Collectors.toList());
        // Convierte la tupla a un DTO
        dto.setNutrients(nutrientsOfFood);
        dto.noMessageAndStatusCode();
        return dto;
    }
    public ResponseDTO getNutrientsByFoodId(Long id, Double quantity) {
        DetailedFoodDTO dto = foodRepository.findDetailedFood(id);
        if (dto == null)
            return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        dto.setQuantity(quantity);
        List<Object[]> nutrients = foodRepository.findNutrientsOfFood(id);
        if (nutrients.isEmpty())
            return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        // Convierte la tupla a una lista de DTOs
        List<NutrientDTO> nutrientsOfFood = nutrients.stream()
            .map(n -> new NutrientDTO(
                (Long) n[0],
                (String) n[1],
                this.round((Double) n[2]),
                (UnitOfMeasurementEnum) n[3],
                (String) n[4]
            ))
            .collect(Collectors.toList());
        return new ListNutrientsDTO(null, 200, ResponseType.SUCCESS, nutrientsOfFood);
    }

    // ? Funciones auxiliares
    // Método para redondear un valor de forma concisa
    public Double round(Double value) {
        if (value == null) return null;
        return Double.valueOf(String.format("%.3f", value));
    }
    // Método para convertir una entidad a un DTO
    private SearchFoodDTO convertToSearchFoodDTO(String username, FoodEntity foodEntity) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        UserEntity user = userOpt.get();
        return SearchFoodDTO.builder()
            .foodId(foodEntity.getId())
            .foodName(foodEntity.getName())
            .foodCategory(foodEntity.getCategoryFood().getName())
            .information(foodEntity.getInformation())
            .imgUrl(foodEntity.getImgUrl())
            .isFavorite(isFavoriteFoodById(user, foodEntity.getId()))
            .build();
    }
    private Boolean isFavoriteFoodById(UserEntity user, Long foodId) {
        return user.getFavoriteFoods().stream().anyMatch(f -> f.getId().equals(foodId));
    }
    
}
