package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaloriesConsumedLastWeekDTO extends ResponseDTO {
    private Double totalCalories;

    @Builder
    public CaloriesConsumedLastWeekDTO(String message, Integer statusCode, ResponseType type, Double totalCalories) {
        super(message, statusCode, type);
        this.totalCalories = totalCalories;
    }
}
