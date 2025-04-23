package xyz.brianuceda.monolithfood_backend.dtos.searches;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.enums.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NutrientDTO {
    private Long id;
    private String name;
    private Double nutrientQuantity;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private String color;
}
