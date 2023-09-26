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
    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;
}
