package xyz.brianuceda.monolithfood_backend.dtos.foodintake;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.brianuceda.monolithfood_backend.enums.UnitOfMeasurementEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewIntakeDTO {
    private Long foodId;
    private Double quantity;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private Timestamp date;
}
