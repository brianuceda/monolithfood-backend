package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FitnessInfoDTO extends ResponseDTO {
    private Double targetWeightKg;
    private Timestamp targetDate;

    public FitnessInfoDTO(String message, Integer statusCode, ResponseType type, Double targetWeightKg, Timestamp targetDate) {
        super(message, statusCode, type);
        this.targetWeightKg = targetWeightKg;
        this.targetDate = targetDate;
    }
}
