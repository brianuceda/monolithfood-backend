package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "composition_recipe")
public class CompositionRecipeEntity {
    @EmbeddedId
    private CompositionRecipeKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("recipeId")
    @JoinColumn(nullable = false, name = "recipe_id")
    private RecipeEntity recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("nutrientId")
    @JoinColumn(nullable = false, name = "nutrient_id")
    private NutrientEntity nutrient;

    @Column(nullable = false)
    private Double nutrientQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UnitOfMeasurementEnum unitOfMeasurement;

}