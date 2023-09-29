package pe.edu.upc.MonolithFoodApplication.dtos.userpersonal;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoRequestDTO {
    private GenderEnum gender;
    private Timestamp birthdate;
}
