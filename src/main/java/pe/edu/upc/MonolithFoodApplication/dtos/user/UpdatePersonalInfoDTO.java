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
public class UpdatePersonalInfoDTO extends ResponseDTO {
    private GenderEnum gender;
    private Timestamp borndate;

    @Builder
    public UpdatePersonalInfoDTO(String message, Integer statusCode, GenderEnum gender, Timestamp borndate, Double heightCm, Double weightKg) {
        super(message, statusCode);
        this.gender = gender;
        this.borndate = borndate;
    }
}
