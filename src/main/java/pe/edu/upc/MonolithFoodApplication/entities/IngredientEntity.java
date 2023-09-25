package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredient")
public class IngredientEntity {
    @EmbeddedId
    private IngredientKey id;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity food;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    @Column(nullable = false)
    private Double ingredientQuantity;

}
