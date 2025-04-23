package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListNutrientsDTO extends ResponseDTO {
    List<NutrientDTO> nutrients;

    public ListNutrientsDTO(String message, Integer statusCode, ResponseType type, List<NutrientDTO> nutrients) {
        super(message, statusCode, type);
        this.nutrients = nutrients;
    }
}
