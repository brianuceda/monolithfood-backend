package xyz.brianuceda.monolithfood_backend.dtos.fitnessinfo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
