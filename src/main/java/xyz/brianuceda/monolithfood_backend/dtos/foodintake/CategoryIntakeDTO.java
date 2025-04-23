package xyz.brianuceda.monolithfood_backend.dtos.foodintake;

import java.util.List;

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
public class CategoryIntakeDTO extends ResponseDTO {
    private MacrosPerCategoryDTO macrosConsumedPerCategory;
    private List<IntakeDTO> myIntakes;

    public CategoryIntakeDTO(String message, Integer statusCode, ResponseType type, MacrosPerCategoryDTO macrosConsumedPerCategory, List<IntakeDTO> myIntakes) {
        super(message, statusCode, type);
        this.macrosConsumedPerCategory = macrosConsumedPerCategory;
        this.myIntakes = myIntakes;
    }
}
