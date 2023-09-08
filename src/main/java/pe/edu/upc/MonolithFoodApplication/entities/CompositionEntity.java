package pe.edu.upc.MonolithFoodApplication.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Brian

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
    @JoinColumn(name = "food_id")
    private FoodEntity food;

    @ManyToOne
    @MapsId("nutrientId")
    @JoinColumn(name = "nutrient_id")
    private NutrientEntity nutrient;

    private Double nutrientQuantity;

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class CompositionKey implements Serializable {
    
    @Column(name = "food_id")
    private Long foodId;

    @Column(name = "nutrient_id")
    private Long nutrientId;

}
