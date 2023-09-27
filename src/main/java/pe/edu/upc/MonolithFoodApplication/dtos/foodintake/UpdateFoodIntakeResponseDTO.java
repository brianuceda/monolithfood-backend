package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodIntakeResponseDTO {
    private String foodName;
    private Double oldQuantity;
    private Double newQuantity;
    private Double totalCaloriesDifference;
}

