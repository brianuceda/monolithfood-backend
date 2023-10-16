package pe.edu.upc.MonolithFoodApplication.dtos.searches;

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
    private Long foodId;
    private String foodName;
    private List<FoodCompositionDTO> composition;

}
