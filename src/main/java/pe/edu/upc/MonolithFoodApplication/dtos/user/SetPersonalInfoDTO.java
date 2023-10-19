package pe.edu.upc.MonolithFoodApplication.dtos.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetPersonalInfoDTO extends ResponseDTO {
    private GenderEnum gender;
    private Timestamp borndate;
    private Double weightKg;
    private Double heightCm;

    @Builder
    public SetPersonalInfoDTO(String message, Integer statusCode, GenderEnum gender, Timestamp borndate, Double weightKg, Double heightCm) {
        super(message, statusCode);
        this.gender = gender;
        this.borndate = borndate;
        this.weightKg = weightKg;
        this.heightCm = heightCm;

    }
}
