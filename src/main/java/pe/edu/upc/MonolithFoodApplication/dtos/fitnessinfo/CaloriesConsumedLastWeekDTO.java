package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaloriesConsumedLastWeekDTO extends ResponseDTO {
    private Double totalCalories;

    @Builder
    public CaloriesConsumedLastWeekDTO(String message, Integer statusCode, Double totalCalories) {
        super(message, statusCode);
        this.totalCalories = totalCalories;
    }
}
