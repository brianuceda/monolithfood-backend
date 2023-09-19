package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPersonalInfoDTO {
    private GenderEnum gender;
    private Timestamp birthdate;
    private Double heightCm;
    private Double weightKg;
}
