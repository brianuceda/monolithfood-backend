package xyz.brianuceda.monolithfood_backend.dtos.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MacrosPerWeek extends ResponseDTO {
    private MacrosPerDays calories;
    private MacrosPerDays proteins;
    private MacrosPerDays carbohydrates;
    private MacrosPerDays fats;
    
    public MacrosPerWeek(String message, Integer statusCode, ResponseType type, MacrosPerDays calories, MacrosPerDays proteins, MacrosPerDays carbohydrates, MacrosPerDays fats) {
        super(message, statusCode, type);
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }
}
