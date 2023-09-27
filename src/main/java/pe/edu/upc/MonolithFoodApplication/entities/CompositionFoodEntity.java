package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composition_food")
public class CompositionFoodEntity {
    @EmbeddedId
    private CompositionFoodKey id;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity food;

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