package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MacrosDetailedDTO extends ResponseDTO {
    private Double consumedCalories;
    private Double dailyCaloricIntake;
    private Double consumedProteins;
    private Double dailyProteinIntake;
    private Double consumedCarbohydrates;
    private Double dailyCarbohydrateIntake;
    private Double consumedFats;
    private Double dailyFatIntake;

    public MacrosDetailedDTO
    (
        String message, Integer statusCode, ResponseType type,
        Double consumedCalories, Double dailyCaloricIntake,
        Double consumedProteins, Double dailyProteinIntake,
        Double consumedCarbohydrates, Double dailyCarbohydrateIntake,
        Double consumedFats, Double dailyFatIntake
    ) {
        super(message, statusCode, type);
        this.consumedCalories = consumedCalories;
        this.dailyCaloricIntake = dailyCaloricIntake;
        this.consumedProteins = consumedProteins;
        this.dailyProteinIntake = dailyProteinIntake;
        this.consumedCarbohydrates = consumedCarbohydrates;
        this.dailyCarbohydrateIntake = dailyCarbohydrateIntake;
        this.consumedFats = consumedFats;
        this.dailyFatIntake = dailyFatIntake;
    }

    // Método para redondear un valor de forma concisa
    private Double round(Double value) {
        if (value == null) return null;
        return Double.valueOf(String.format("%.0f", value));
    }

    // Método para redondear todos los campos
    public void roundAllValues() {
        this.consumedCalories = round(this.consumedCalories);
        this.dailyCaloricIntake = round(this.dailyCaloricIntake);
        this.consumedProteins = round(this.consumedProteins);
        this.dailyProteinIntake = round(this.dailyProteinIntake);
        this.consumedCarbohydrates = round(this.consumedCarbohydrates);
        this.dailyCarbohydrateIntake = round(this.dailyCarbohydrateIntake);
        this.consumedFats = round(this.consumedFats);
        this.dailyFatIntake = round(this.dailyFatIntake);
    }

    public void noMessageAndStatusCode() {
        this.setMessage(null);
        this.setStatusCode(200);
    }
}
