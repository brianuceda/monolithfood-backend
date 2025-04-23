package xyz.brianuceda.monolithfood_backend.dtos.fitnessinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
