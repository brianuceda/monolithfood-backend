package pe.edu.upc.MonolithFoodApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodNutrientDTO {
    private String foodName;
    private String nutrientName;
    private Double nutrientQuantity;

}
