package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPersonallnfoDto {

    private Long id;
    private GenderEnum gender;
    private Date birthdate;
    private Double heightCm;
    private Double weightKg;
    private ActivityLevelEntity activityLevel;
    private UserEntity user;

}
