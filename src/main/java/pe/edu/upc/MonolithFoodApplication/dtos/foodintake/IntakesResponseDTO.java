package pe.edu.upc.MonolithFoodApplication.dtos.foodintake;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntakesResponseDTO extends ResponseDTO {
    private MacrosDetailedDTO macros;
    private AllCategoriesIntakesDTO categories;

    public IntakesResponseDTO(String message, Integer statusCode, MacrosDetailedDTO macros, AllCategoriesIntakesDTO categories) {
        super(message, statusCode);
        this.macros = macros;
        this.categories = categories;
    }
}
