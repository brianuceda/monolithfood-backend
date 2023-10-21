package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composition_food")
public class CompositionFoodEntity {
    @EmbeddedId
    private CompositionFoodKey id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("foodId")
    @JoinColumn(nullable = false, name = "food_id")
    private FoodEntity food;

    @ToString.Exclude
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
