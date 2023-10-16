package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodNutrientDTO {
    private Long foodId;
    private String foodName;
    private Double nutrientQuantity;

}
