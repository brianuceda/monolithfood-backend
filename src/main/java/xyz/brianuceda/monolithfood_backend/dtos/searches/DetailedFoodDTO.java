package xyz.brianuceda.monolithfood_backend.dtos.searches;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedFoodDTO extends ResponseDTO {
    private Long id;
    private String name;
    private String categoryFood;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private Double quantity;
    private List<NutrientDTO> nutrients;

    public DetailedFoodDTO(
        Long id,
        String name,
        String categoryFood,
        Timestamp date
    ) {
        this.id = id;
        this.name = name;
        this.categoryFood = categoryFood;
    }

    public void noMessageAndStatusCode() {
        this.setMessage(null);
        this.setStatusCode(200);
    }
}