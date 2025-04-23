package xyz.brianuceda.monolithfood_backend.dtos.searches;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
