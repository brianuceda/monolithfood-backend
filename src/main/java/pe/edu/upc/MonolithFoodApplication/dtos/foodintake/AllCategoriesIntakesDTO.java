package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllCategoriesIntakesDTO {
    private CategoryIntakeDTO desayuno;
    private CategoryIntakeDTO almuerzo;
    private CategoryIntakeDTO cena;
}
