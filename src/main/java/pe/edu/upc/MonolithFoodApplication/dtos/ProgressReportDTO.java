package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor
@NoArgsConstructor
public class ProgressReportDTO {
    private Double caloriesTotal;
    private Double macronutrients;

    private FoodLiostDTO foodList;
}
