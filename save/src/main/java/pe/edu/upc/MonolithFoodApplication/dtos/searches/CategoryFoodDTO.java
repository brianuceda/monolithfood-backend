package pe.edu.upc.MonolithFoodApplication.dtos.searches;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFoodDTO {
    private String categoryName;
    private String information;
    private String benefits;
    private String disadvantages;
}
