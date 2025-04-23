package xyz.brianuceda.monolithfood_backend.dtos.foodintake;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.NutrientDTO;
import xyz.brianuceda.monolithfood_backend.enums.CategoryIntakeEnum;
import xyz.brianuceda.monolithfood_backend.enums.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedIntakeDTO extends ResponseDTO {
    private Long id;
    private String name;
    private CategoryIntakeEnum categoryIntake;
    private String categoryFood;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private Double quantity;
    private Timestamp date;
    private List<NutrientDTO> nutrients;
    private Long foodId;

    public DetailedIntakeDTO(
        Long id,
        String name,
        CategoryIntakeEnum categoryIntake,
        String categoryFood,
        UnitOfMeasurementEnum unitOfMeasurement,
        Double quantity,
        Timestamp date,
        Long foodId
    ) {
        this.id = id;
        this.name = name;
        this.categoryIntake = categoryIntake;
        this.categoryFood = categoryFood;
        this.unitOfMeasurement = unitOfMeasurement;
        this.quantity = quantity;
        this.date = date;
        this.foodId = foodId;
    }

    public void noMessageAndStatusCode() {
        this.setMessage(null);
        this.setStatusCode(200);
    }

    public void addHours(Integer hours) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusHours(hours);
        this.date = Timestamp.valueOf(localDateTime);
    }

    public void minusHours(Integer hours) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.minusHours(hours);
        this.date = Timestamp.valueOf(localDateTime);
    }
}
