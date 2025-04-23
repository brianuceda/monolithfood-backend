package xyz.brianuceda.monolithfood_backend.dtos.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.GenderEnum;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetInformationDTO extends ResponseDTO {
    private GenderEnum gender;
    private Timestamp borndate;
    private Double weightKg;
    private Double heightCm;
    // Fitness goal
    private Double targetWeightKg;
    private Timestamp targetDate;

    @Builder
    public SetInformationDTO(String message, Integer statusCode, ResponseType type, GenderEnum gender, Timestamp borndate, Double weightKg, Double heightCm, Double targetWeightKg, Timestamp targetDate) {
        super(message, statusCode, type);
        this.gender = gender;
        this.borndate = borndate;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.targetWeightKg = targetWeightKg;
        this.targetDate = targetDate;
    }
}
