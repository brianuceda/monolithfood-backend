package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import java.util.List;

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
public class DetailedFoodDTO extends ResponseDTO {
    private Long id;
    private String name;
    private String information;
    private String imgUrl;
    private String sourceOfOrigin;
    private CategoryFoodDTO categoryFood;
    private List<NutrientDTO> nutrients;

    public void noMessageAndStatusCode() {
        this.setMessage(null);
        this.setStatusCode(200);
    }
}
