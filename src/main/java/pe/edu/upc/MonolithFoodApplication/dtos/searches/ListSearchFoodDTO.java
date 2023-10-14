package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ListSearchFoodDTO extends ResponseDTO {
    private List<SearchFoodDTO> foundFoods;

    @Builder
    public ListSearchFoodDTO(String message, Integer statusCode, List<SearchFoodDTO> foundFoods) {
        super(message, statusCode);
        this.foundFoods = foundFoods;
    }
}
