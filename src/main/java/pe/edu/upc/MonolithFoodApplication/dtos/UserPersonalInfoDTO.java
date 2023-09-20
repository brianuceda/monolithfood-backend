package pe.edu.upc.MonolithFoodApplication.dtos;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPersonalInfoDTO {
    private GenderEnum gender;
    private Timestamp birthdate;
    private Double heightCm;
    private Double weightKg;
}
