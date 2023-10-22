package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryIntakeEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedIntakeDTO extends ResponseDTO {
    private Long id;
    private String name;
    private CategoryIntakeEnum categoryIntake;
    private String categoryFood;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private Double quantity;
    private Timestamp date;
    private Double calories;
    private Double proteins;
    private Double carbohydrates;
    private Double fats;
    
    // Método para redondear un valor de forma concisa
    private Double round(Double value) {
        if (value == null) return null;
        return Double.valueOf(String.format("%.2f", value));
    }

    // Método para redondear todos los campos
    public void roundAllValues() {
        this.calories = round(this.calories);
        this.proteins = round(this.proteins);
        this.carbohydrates = round(this.carbohydrates);
        this.fats = round(this.fats);
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
