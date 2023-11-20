package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FitnessInfoResponseDTO extends ResponseDTO {
    private GenderEnum gender;
    private Integer age;
    private Double height;
    private Double weight;
    private Double targetWeightKg;
    private Timestamp targetDate;
    private String activityLevel;
    private Double activityLevelQuotient;
    private Double imc;
    private String clasification;
    private Double dailyCaloricIntake;
    private Double dailyProteinIntake;
    private Double dailyCarbohydrateIntake;
    private Double dailyFatIntake;
    private Double avgProteinPerKg;
    private Double tmb;
    
    @Builder
    public FitnessInfoResponseDTO(String message, Integer statusCode, ResponseType type, GenderEnum gender, Integer age, Double height, Double weight, Double targetWeightKg, Timestamp targetDate, String activityLevel, Double activityLevelQuotient, Double imc, String clasification, Double dailyCaloricIntake, Double dailyProteinIntake, Double dailyCarbohydrateIntake, Double dailyFatIntake, Double avgProteinPerKg, Double tmb) {
        super(message, statusCode, type);
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.targetWeightKg = targetWeightKg;
        this.targetDate = targetDate;
        this.activityLevel = activityLevel;
        this.activityLevelQuotient = activityLevelQuotient;
        this.imc = imc;
        this.clasification = clasification;
        this.dailyCaloricIntake = dailyCaloricIntake;
        this.dailyProteinIntake = dailyProteinIntake;
        this.dailyCarbohydrateIntake = dailyCarbohydrateIntake;
        this.dailyFatIntake = dailyFatIntake;
        this.avgProteinPerKg = avgProteinPerKg;
        this.tmb = tmb;
    }
}
