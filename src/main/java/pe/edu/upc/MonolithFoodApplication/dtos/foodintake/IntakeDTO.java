package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import java.sql.Timestamp;

// import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntakeDTO extends ResponseDTO {
    private Long id;
    private String name;
    private String imgUrl;
    private Double quantity;
    private UnitOfMeasurementEnum unitOfMeasurement;
    private Timestamp createdAt;

    public IntakeDTO(Long id, String name, String imgUrl, Double quantity, UnitOfMeasurementEnum unitOfMeasurement, Timestamp createdAt) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
        this.unitOfMeasurement = unitOfMeasurement;
        this.createdAt = createdAt;
    }

    public IntakeDTO(String message) {
        super(message, null, null);
    }
}
