package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor
@NoArgsConstructor
public class ProgressReportDTO {
     //total de calorias consumidas durante la semana 
    private Double caloriesTotal;

}
