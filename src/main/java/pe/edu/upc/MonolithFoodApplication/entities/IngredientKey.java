package pe.edu.upc.MonolithFoodApplication.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IngredientKey implements Serializable {
    @Column(nullable = false, name = "food_id")
    private Long foodId;

    @Column(nullable = false, name = "recipe_id")
    private Long recipeId;
}
