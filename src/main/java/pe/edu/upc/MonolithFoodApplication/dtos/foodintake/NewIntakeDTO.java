package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewIntakeDTO {
    private Long foodId;
    private Double quantity;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private Timestamp date;
}
