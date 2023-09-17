package pe.edu.upc.MonolithFoodApplication.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.dtos.CaloricIntakeAlertDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.FoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.FoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RemoveFoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RemoveFoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.UpdateFoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.UpdateFoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.NutrientRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private NutrientRepository nutrientRepository;

    @Autowired
    private EatRepository eatRepository;




    public FoodIntakeResponseDTO addFoodIntake(FoodIntakeDTO foodIntakeDTO) {
        FoodEntity food = foodRepository.findById(foodIntakeDTO.getFoodId())
            .orElseThrow(() -> new RuntimeException("Food not found"));

        Double caloriesPerUnit = foodRepository.findCaloriesByFoodId(food.getId())
            .orElseThrow(() -> new RuntimeException("Calories not found for food"))
            .doubleValue();

        Double totalCalories = caloriesPerUnit * foodIntakeDTO.getQuantity();

        // Aquí se puede agregar el alimento a la ingesta del usuario y guardar en la base de datos si es necesario

        return new FoodIntakeResponseDTO(food.getName(), foodIntakeDTO.getQuantity(), totalCalories);
    }

    public UpdateFoodIntakeResponseDTO updateFoodIntake(UpdateFoodIntakeDTO updateFoodIntakeDTO) {
        FoodEntity food = foodRepository.findById(updateFoodIntakeDTO.getFoodId())
            .orElseThrow(() -> new RuntimeException("Food not found"));

        Double caloriesPerUnit = foodRepository.findCaloriesByFoodId(food.getId())
            .orElseThrow(() -> new RuntimeException("Calories not found for food"))
            .doubleValue();

        // Se necesita obtener la cantidad anterior del alimento en la ingesta diaria del usuario.
        // Por simplicidad, se usara un valor fijo, pero se deberida de obtener de la base de datos.
        Double oldQuantity = 1.0; // Este valor debería ser obtenido de la base de datos.

        Double totalCaloriesDifference = caloriesPerUnit * (updateFoodIntakeDTO.getNewQuantity() - oldQuantity);

        // Aquí se puede actualizar la cantidad en la ingesta diaria del usuario y guardar en la base de datos.

        return new UpdateFoodIntakeResponseDTO(food.getName(), oldQuantity, updateFoodIntakeDTO.getNewQuantity(), totalCaloriesDifference);
    }
    
    public RemoveFoodIntakeResponseDTO removeFoodIntake(RemoveFoodIntakeDTO removeFoodIntakeDTO) {
        FoodEntity food = foodRepository.findById(removeFoodIntakeDTO.getFoodId())
            .orElseThrow(() -> new RuntimeException("Food not found"));

        Double caloriesPerUnit = foodRepository.findCaloriesByFoodId(food.getId())
            .orElseThrow(() -> new RuntimeException("Calories not found for food"))
            .doubleValue();

        // Se necesita obtener la cantidad anterior del alimento en la ingesta diaria del usuario.
        // Por simplicidad, se usara un valor fijo, pero se deberida de obtener de la base de datos.
        Double quantity = 1.0; // Este valor debería ser obtenido de la base de datos.

        Double removedCalories = caloriesPerUnit * quantity;

        // Aquí puedes eliminar el alimento de la ingesta diaria del usuario y guardar en la base de datos.

        return new RemoveFoodIntakeResponseDTO(food.getName(), removedCalories);
    }


    public CaloricIntakeAlertDTO checkCaloricIntake( String username  ) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        String userId = user.get().getId().toString();
        
    // Aquí, necesitas obtener el límite calórico recomendado para el usuario.
    // Por simplicidad, usaré un valor fijo, pero deberías obtenerlo de la base de datos.
    Double recommendedCaloricLimit = 2000.0; // Este valor debería ser obtenido de la base de datos.


    //ARREGLAR URGENTE AQUI ME QUEDO HOY 16/09
    Double totalCaloriesConsumed = eatRepository.findTotalCaloriesByUserIdAndDate(userId, LocalDate.now());
       // .orElse(0.0);

    Boolean hasExceededLimit = totalCaloriesConsumed > recommendedCaloricLimit;
    Double exceededAmount = hasExceededLimit ? totalCaloriesConsumed - recommendedCaloricLimit : 0.0;

    return new CaloricIntakeAlertDTO(hasExceededLimit, totalCaloriesConsumed, recommendedCaloricLimit, exceededAmount);
    }


                
}



