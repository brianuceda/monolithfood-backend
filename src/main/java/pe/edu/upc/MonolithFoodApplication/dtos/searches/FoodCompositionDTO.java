package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCompositionDTO {
    private String nutrientName;
    private Double nutrientQuantity;
}
