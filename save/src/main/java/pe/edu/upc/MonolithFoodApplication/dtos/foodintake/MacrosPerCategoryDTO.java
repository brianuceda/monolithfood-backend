package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MacrosPerCategoryDTO {
    private Double consumedCalories;
    private Double consumedProteins;
    private Double consumedCarbohydrates;
    private Double consumedFats;

    // Método para redondear un valor de forma concisa
    private Double round(Double value) {
        if (value == null) return null;
        return Double.valueOf(String.format("%.0f", value));
    }

    // Método para redondear todos los campos
    public void roundAllValues() {
        this.consumedCalories = round(this.consumedCalories);
        this.consumedProteins = round(this.consumedProteins);
        this.consumedCarbohydrates = round(this.consumedCarbohydrates);
        this.consumedFats = round(this.consumedFats);
    }
}
