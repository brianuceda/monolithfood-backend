package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(nullable = false, name = "food_id")
    private FoodEntity food;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(nullable = false, name = "recipe_id")
    private RecipeEntity recipe;

    @Column(nullable = false)
    private Double ingredientQuantity;

}
