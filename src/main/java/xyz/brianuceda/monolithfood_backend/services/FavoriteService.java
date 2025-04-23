package xyz.brianuceda.monolithfood_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.ListSearchFoodDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.SearchFoodDTO;
import xyz.brianuceda.monolithfood_backend.entities.FoodEntity;
import xyz.brianuceda.monolithfood_backend.entities.UserEntity;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.repositories.FoodRepository;
import xyz.brianuceda.monolithfood_backend.repositories.UserRepository;

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
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = userOpt.get();
        Optional<FoodEntity> foodOpt = foodRepository.findById(foodId);
        if (!foodOpt.isPresent()) {
            return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        FoodEntity food = foodOpt.get();
        if (user.getFavoriteFoods().contains(food)) {
            return new ResponseDTO("El alimento ya está en tus favoritos", HttpStatus.OK.value(), ResponseType.INFO);
        }
        user.getFavoriteFoods().add(food);
        userRepository.save(user);
        return new ResponseDTO("Alimento agregado a favoritos", HttpStatus.OK.value(), ResponseType.FAVORITE);
    }
    // Get: Obtener todos los alimentos favoritos de un usuario
    @Transactional
    public ResponseDTO getAllFavoriteFoods(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = userOpt.get();
        List<FoodEntity> favoriteFoods = user.getFavoriteFoods();
        if (favoriteFoods.isEmpty()) {
            return new ResponseDTO("No tienes alimentos favoritos", HttpStatus.OK.value(), ResponseType.INFO);
        }
        List<SearchFoodDTO> searchFoodDTOs = favoriteFoods.stream().map(f -> {
            SearchFoodDTO searchFoodDTO = this.convertToSearchFoodDTO(user, f);
            return searchFoodDTO;
        }).collect(Collectors.toList());
        return new ListSearchFoodDTO(null, 200, null, searchFoodDTOs);
    }
    // Delete: Eliminar un alimento de favoritos
    @Transactional
    public ResponseDTO deleteFavoriteFood(String username, Long foodId) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = userOpt.get();
        Optional<FoodEntity> foodOpt = foodRepository.findById(foodId);
        if (!foodOpt.isPresent()) {
            return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        FoodEntity food = foodOpt.get();
        if (!user.getFavoriteFoods().contains(food)) {
            return new ResponseDTO("No tienes ese alimento en favoritos", HttpStatus.OK.value(), ResponseType.INFO);
        }
        user.getFavoriteFoods().remove(food);
        userRepository.save(user);
        return new ResponseDTO("Alimento eliminado de favoritos", HttpStatus.OK.value(), ResponseType.SUCCESS);
    }

    // ? Funciones auxiliares
    // Método para convertir una entidad a un DTO
    private SearchFoodDTO convertToSearchFoodDTO(UserEntity user, FoodEntity foodEntity) {
        return SearchFoodDTO.builder()
            .foodId(foodEntity.getId())
            .foodName(foodEntity.getName())
            .information(foodEntity.getInformation())
            .imgUrl(foodEntity.getImgUrl())
            .isFavorite(isFavoriteFoodById(user, foodEntity.getId()))
            .build();
    }
    private Boolean isFavoriteFoodById(UserEntity user, Long foodId) {
        return user.getFavoriteFoods().stream().anyMatch(f -> f.getId().equals(foodId));
    }
}
