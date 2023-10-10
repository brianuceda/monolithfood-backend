package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvgDayCalDTO {
    private String username;
    private String date;
    private Double averageCaloriesDay;
    
}
