package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntakesResponseDTO extends ResponseDTO {
    private MacrosDetailedDTO macros;
    private AllCategoriesIntakesDTO categories;

    public IntakesResponseDTO(String message, Integer statusCode, ResponseType type, MacrosDetailedDTO macros, AllCategoriesIntakesDTO categories) {
        super(message, statusCode, type);
        this.macros = macros;
        this.categories = categories;
    }
}
