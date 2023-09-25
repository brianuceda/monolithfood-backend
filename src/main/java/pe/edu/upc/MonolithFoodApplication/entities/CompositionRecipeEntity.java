package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composition_recipe")
public class CompositionRecipeEntity {
    @EmbeddedId
    private CompositionRecipeKey id;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    @ManyToOne
    @MapsId("nutrientId")
    @JoinColumn(name = "nutrient_id", nullable = false)
    private NutrientEntity nutrient;

    @Column(nullable = false)
    private Double nutrientQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UnitOfMeasurementEnum unitOfMeasurement;

}