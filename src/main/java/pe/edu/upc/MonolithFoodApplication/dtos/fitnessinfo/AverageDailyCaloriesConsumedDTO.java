package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AverageDailyCaloriesConsumedDTO {
    private String username;
    private String date;
    private Double averageCaloriesDay;
    // Considerar mostrar tambien la lista de productos con calorias consumidas en ese dia
}
