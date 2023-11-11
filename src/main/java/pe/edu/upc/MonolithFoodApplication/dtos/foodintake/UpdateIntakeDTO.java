package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.enums.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIntakeDTO extends NewIntakeDTO {
    private Long eatId;

    public UpdateIntakeDTO(Long foodId, Double quantity, UnitOfMeasurementEnum unitOfMeasurement, Timestamp date, Long eatId) {
        super(foodId, quantity, unitOfMeasurement, date);
        this.eatId = eatId;
    }
}

