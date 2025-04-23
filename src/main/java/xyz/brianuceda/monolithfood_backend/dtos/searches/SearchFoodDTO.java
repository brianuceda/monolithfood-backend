package xyz.brianuceda.monolithfood_backend.dtos.searches;

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
    private String foodCategory;
    private String information;
    private String imgUrl;
    private Boolean isFavorite;
}
