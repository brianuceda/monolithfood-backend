package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ListFoodNutrientDTO extends ResponseDTO {
    private List<FoodNutrientDTO> foods;

    @Builder
    public ListFoodNutrientDTO(String message, Integer statusCode, List<FoodNutrientDTO> foods) {
        super(message, statusCode);
        this.foods = foods;
    }
}
