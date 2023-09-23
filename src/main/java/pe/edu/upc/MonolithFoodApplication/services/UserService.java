package pe.edu.upc.MonolithFoodApplication.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.dtos.CaloricIntakeAlertDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.FoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.FoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RemoveFoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RemoveFoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.UpdateFoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.UpdateFoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionEntity;
import pe.edu.upc.MonolithFoodApplication.entities.EatEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.NutrientEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.CompositionRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;


    @Autowired
    private EatRepository eatRepository;

    @Autowired
    private CompositionRepository compositionRepository;


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

    public CaloricIntakeAlertDTO checkDailyCaloricIntake(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Obtener el total de calorías consumidas por el usuario en el día.
        List<EatEntity> todaysEats = eatRepository.findByUsernameAndDate(username, LocalDate.now());
        double totalCaloriesConsumed = 0;
        for (EatEntity eat : todaysEats) {
            FoodEntity food = eat.getFood();
            List<CompositionEntity> compositions = compositionRepository.findByFood(food);
            for (CompositionEntity composition : compositions) {
                NutrientEntity nutrient = composition.getNutrient();
                if ("Calories".equalsIgnoreCase(nutrient.getName())) {
                    totalCaloriesConsumed += composition.getNutrientQuantity();
                }
            }
        }

        // 2. Comparar ese total con las calorías recomendadas para el usuario.
        UserPersonalInfoEntity userPersonalInfo = user.getUserPersonalInfo();
        double recommendedCaloricLimit = userPersonalInfo.getDailyCaloricIntake();

        // 3. Si las calorías consumidas superan las recomendadas, mostrar una alerta.
        String message;
        double exceededAmount = totalCaloriesConsumed - recommendedCaloricLimit;
        if (totalCaloriesConsumed > recommendedCaloricLimit) {
            message = "Alert: You have exceeded your recommended daily caloric intake!";
        } else {
            message = "You are within your recommended daily caloric intake.";
            exceededAmount = 0.0;
        }

        return new CaloricIntakeAlertDTO(totalCaloriesConsumed > recommendedCaloricLimit, totalCaloriesConsumed, recommendedCaloricLimit, exceededAmount, message);
    }
    
    
                
}



