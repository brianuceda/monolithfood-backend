package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntakeDTO {
    private Long id;
    private String name;
    private String category;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private Double quantity;
    private Double calories;
    private Timestamp date;
}
