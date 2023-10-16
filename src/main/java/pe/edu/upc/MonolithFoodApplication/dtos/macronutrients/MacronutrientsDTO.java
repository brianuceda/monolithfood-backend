package pe.edu.upc.MonolithFoodApplication.dtos.macronutrients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MacronutrientsDTO extends ResponseDTO {
    private Double consumedDailyCaloricIntake = 0.0;
    private Double dailyCaloricIntake;

    private Double consumedDailyProteinIntake = 0.0;
    private Double dailyProteinIntake;

    private Double consumedDailyCarbohydrateIntake = 0.0;
    private Double dailyCarbohydrateIntake;
    
    private Double consumedDailyFatIntake = 0.0;
    private Double dailyFatIntake;

    @Builder
    public MacronutrientsDTO(String message, Integer status, Double consumedDailyCaloricIntake, Double dailyCaloricIntake, Double consumedDailyProteinIntake, Double dailyProteinIntake, Double consumedDailyCarbohydrateIntake, Double dailyCarbohydrateIntake, Double consumedDailyFatIntake, Double dailyFatIntake) {
        super(message, status);
        this.consumedDailyCaloricIntake = consumedDailyCaloricIntake;
        this.dailyCaloricIntake = dailyCaloricIntake;
        this.consumedDailyProteinIntake = consumedDailyProteinIntake;
        this.dailyProteinIntake = dailyProteinIntake;
        this.consumedDailyCarbohydrateIntake = consumedDailyCarbohydrateIntake;
        this.dailyCarbohydrateIntake = dailyCarbohydrateIntake;
        this.consumedDailyFatIntake = consumedDailyFatIntake;
        this.dailyFatIntake = dailyFatIntake;
    }

}
