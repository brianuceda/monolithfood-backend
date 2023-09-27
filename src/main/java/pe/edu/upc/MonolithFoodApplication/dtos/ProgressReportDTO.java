package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor
@NoArgsConstructor
public class ProgressReportDTO {
     //total de calorias consumidas durante la semana 
    private Double caloriesTotal;
    // //promedio de claorias diaras consumindas por dia 
    // private Double averageDailyCalories;
    // //calorias consumidas por dia, retorna solo por dia.
    // private Double dailyCalories;
    // //lista de macronutrientes consumidos con la cantidad consumida
    // private MacronutriendDTO macronutrients;

    // //lista de comidas
    // private FoodLiostDTO foodList;


}
