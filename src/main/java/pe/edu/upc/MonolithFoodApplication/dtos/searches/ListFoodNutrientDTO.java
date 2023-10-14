package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ListFoodNutrientDTO extends ResponseDTO {
    private List<FoodNutrientDTO> foundFoods;

    @Builder
    public ListFoodNutrientDTO(String message, Integer statusCode, List<FoodNutrientDTO> foundFoods) {
        super(message, statusCode);
        this.foundFoods = foundFoods;
    }
}
