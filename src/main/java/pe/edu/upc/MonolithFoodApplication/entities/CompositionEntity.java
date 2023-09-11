package pe.edu.upc.MonolithFoodApplication.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composition")
public class CompositionEntity {
    @EmbeddedId
    private CompositionKey id;

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

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class CompositionKey implements Serializable {
    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(name = "nutrient_id", nullable = false)
    private Long nutrientId;

}
