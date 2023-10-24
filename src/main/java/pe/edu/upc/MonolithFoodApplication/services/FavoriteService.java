package pe.edu.upc.MonolithFoodApplication.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.ListSearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.searches.SearchFoodDTO;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    
    // * Brian (Favoritos)
    // Post: Agregar un alimento a favoritos
    @Transactional
    public ResponseDTO addFavoriteFood(String username, Long foodId) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = userOpt.get();
        Optional<FoodEntity> foodOpt = foodRepository.findById(foodId);
        if (!foodOpt.isPresent()) {
            return new ResponseDTO("Alimento no encontrado.", 404);
        }
        FoodEntity food = foodOpt.get();
        if (user.getFavoriteFoods().contains(food)) {
            return new ResponseDTO("El alimento ya está en tus favoritos.", 200);
        }
        user.getFavoriteFoods().add(food);
        userRepository.save(user);
        return new ResponseDTO("Alimento agregado a favoritos.", 200);
    }
    // Get: Obtener todos los alimentos favoritos de un usuario
    @Transactional
    public ResponseDTO getAllFavoriteFoods(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = userOpt.get();
        List<FoodEntity> favoriteFoods = user.getFavoriteFoods();
        if (favoriteFoods.isEmpty()) {
            return new ResponseDTO("No tienes alimentos favoritos.", 200);
        }
        List<SearchFoodDTO> searchFoodDTOs = favoriteFoods.stream().map(this::convertToSearchFoodDTO).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, searchFoodDTOs);
    }
    // Delete: Eliminar un alimento de favoritos
    @Transactional
    public ResponseDTO deleteFavoriteFood(String username, Long foodId) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = userOpt.get();
        Optional<FoodEntity> foodOpt = foodRepository.findById(foodId);
        if (!foodOpt.isPresent()) {
            return new ResponseDTO("Alimento no encontrado.", 404);
        }
        FoodEntity food = foodOpt.get();
        if (!user.getFavoriteFoods().contains(food)) {
            return new ResponseDTO("El alimento no está en tus favoritos.", 200);
        }
        user.getFavoriteFoods().remove(food);
        userRepository.save(user);
        return new ResponseDTO("Alimento eliminado de favoritos.", 200);
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
}
