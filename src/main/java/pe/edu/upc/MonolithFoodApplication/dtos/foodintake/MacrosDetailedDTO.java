package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MacrosDetailedDTO extends ResponseDTO {
    private Double consumedCalories;
    private Double dailyCaloricIntake;
    private Double percentageCaloricConsumed;
    private Double consumedProteins;
    private Double dailyProteinIntake;
    private Double percentageProteinConsumed;
    private Double consumedCarbohydrates;
    private Double dailyCarbohydrateIntake;
    private Double percentageCarbohydrateConsumed;
    private Double consumedFats;
    private Double dailyFatIntake;
    private Double percentageFatConsumed;

    public MacrosDetailedDTO
    (
        String message, Integer statusCode,
        Double consumedCalories, Double dailyCaloricIntake, Double percentageCaloricConsumed,
        Double consumedProteins, Double dailyProteinIntake, Double percentageProteinConsumed,
        Double consumedCarbohydrates, Double dailyCarbohydrateIntake, Double percentageCarbohydrateConsumed,
        Double consumedFats, Double dailyFatIntake, Double percentageFatConsumed
    ) {
        super(message, statusCode);
        this.consumedCalories = consumedCalories;
        this.dailyCaloricIntake = dailyCaloricIntake;
        this.percentageCaloricConsumed = percentageCaloricConsumed;
        this.consumedProteins = consumedProteins;
        this.dailyProteinIntake = dailyProteinIntake;
        this.percentageProteinConsumed = percentageProteinConsumed;
        this.consumedCarbohydrates = consumedCarbohydrates;
        this.dailyCarbohydrateIntake = dailyCarbohydrateIntake;
        this.percentageCarbohydrateConsumed = percentageCarbohydrateConsumed;
        this.consumedFats = consumedFats;
        this.dailyFatIntake = dailyFatIntake;
        this.percentageFatConsumed = percentageFatConsumed;
    }

    // Método para redondear un valor de forma concisa
    private Double round(Double value) {
        if (value == null) return null;
        return Double.valueOf(String.format("%.2f", value));
    }

    // Método para redondear todos los campos
    public void roundAllValues() {
        this.consumedCalories = round(this.consumedCalories);
        this.dailyCaloricIntake = round(this.dailyCaloricIntake);
        this.percentageCaloricConsumed = round(this.percentageCaloricConsumed);
        this.consumedProteins = round(this.consumedProteins);
        this.dailyProteinIntake = round(this.dailyProteinIntake);
        this.percentageProteinConsumed = round(this.percentageProteinConsumed);
        this.consumedCarbohydrates = round(this.consumedCarbohydrates);
        this.dailyCarbohydrateIntake = round(this.dailyCarbohydrateIntake);
        this.percentageCarbohydrateConsumed = round(this.percentageCarbohydrateConsumed);
        this.consumedFats = round(this.consumedFats);
        this.dailyFatIntake = round(this.dailyFatIntake);
        this.percentageFatConsumed = round(this.percentageFatConsumed);
    }

    public void noMessageAndStatusCode() {
        this.setMessage(null);
        this.setStatusCode(200);
    }
}
