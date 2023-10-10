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
public class PersonalInfoDTO extends ResponseDTO {
    private GenderEnum gender;
    private Timestamp borndate;
    private Double heightCm;
    private Double weightKg;

    @Builder
    public PersonalInfoDTO(String message, Integer statusCode, GenderEnum gender, Timestamp borndate, Double heightCm, Double weightKg) {
        super(message, statusCode);
        this.gender = gender;
        this.borndate = borndate;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
    }
}
