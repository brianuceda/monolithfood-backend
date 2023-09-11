package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "foods")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 255)
    private String information;

    @Column(nullable = true)
    private Boolean isPrivate = false;

    @Column(nullable = true)
    private Boolean isFavorite = false;
    
    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = true)
    private UserEntity creatorUser;
   
    @ManyToOne
    @JoinColumn(name = "category_food_id", nullable = true)
    private CategoryFoodEntity category;

    @OneToMany(
        mappedBy = "food",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<CompositionEntity> compositions;
  
    @ManyToMany(mappedBy = "foods")
    private List<RecipeEntity> recipes;

}
