package pe.edu.upc.MonolithFoodApplication.dtos.userpersonal;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoDTO {
    private GenderEnum gender;
    private Timestamp birthdate;
    private Double heightCm;
    private Double weightKg;
}
