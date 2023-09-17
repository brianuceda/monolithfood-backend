package pe.edu.upc.MonolithFoodApplication.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFoodDTO {
    private String foodName;
    // private String foodCategory;
    private List<FoodCompositionDTO> composition;

}
