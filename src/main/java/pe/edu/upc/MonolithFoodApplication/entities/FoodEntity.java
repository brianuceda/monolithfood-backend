package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Brian | Gabriela | Heather

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 255)
    private String information;

    @Column(nullable = false)
    private Boolean isPrivate = false;

    @Column(nullable = false)
    private Boolean isFavorite = false;

    @ManyToMany(mappedBy = "foods")
    private List<RecipeEntity> recipes;
    
}
