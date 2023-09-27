package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaloricIntakeAlertDTO {
    private Boolean hasExceededLimit;
    private Double totalCaloriesConsumed;
    private Double recommendedCaloricLimit;
    private Double exceededAmount;
    private String message;
    
}

