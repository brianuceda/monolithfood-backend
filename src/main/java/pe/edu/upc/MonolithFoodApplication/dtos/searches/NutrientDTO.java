package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NutrientDTO {
    private String name;
    private Double nutrientQuantity;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private String imgUrl;
}
