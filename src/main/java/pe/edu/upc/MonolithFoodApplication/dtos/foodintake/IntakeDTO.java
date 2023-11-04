package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

// import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntakeDTO extends ResponseDTO {
    private Long id;
    private String name;
    // private String categoryFood;
    private String imgUrl;
    private Double quantity;
    private UnitOfMeasurementEnum unitOfMeasurement;
    // private LocalDateTime date;
    
    // public IntakeDTO(Long id, String name, String categoryFood, UnitOfMeasurementEnum unitOfMeasurement, Double quantity, LocalDateTime date) {
    //     super(null, null, null);
    //     this.id = id;
    //     this.name = name;
    //     this.categoryFood = categoryFood;
    //     this.unitOfMeasurement = unitOfMeasurement;
    //     this.quantity = quantity;
    //     this.date = date;
    // }

    public IntakeDTO(Long id, String name, String imgUrl, Double quantity, UnitOfMeasurementEnum unitOfMeasurement) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public IntakeDTO(String message) {
        super(message, null, null);
    }
}
