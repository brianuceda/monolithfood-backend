package xyz.brianuceda.monolithfood_backend.entities;

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
public class CompositionRecipeKey implements Serializable {
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "nutrient_id", nullable = false)
    private Long nutrientId;

}
