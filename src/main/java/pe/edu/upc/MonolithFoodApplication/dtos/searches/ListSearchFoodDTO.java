package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class ListSearchFoodDTO extends ResponseDTO {
    private List<SearchFoodDTO> foods;

    @Builder
    public ListSearchFoodDTO(String message, Integer statusCode, ResponseType type, List<SearchFoodDTO> foods) {
        super(message, statusCode, type);
        this.foods = foods;
    }
}
