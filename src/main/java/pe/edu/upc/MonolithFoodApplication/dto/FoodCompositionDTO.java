package pe.edu.upc.MonolithFoodApplication.dto;

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
