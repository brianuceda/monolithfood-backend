package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodIntakeResponseDTO {
    private String foodName;
    private Double quantity;
    private Double totalCalories;
}

