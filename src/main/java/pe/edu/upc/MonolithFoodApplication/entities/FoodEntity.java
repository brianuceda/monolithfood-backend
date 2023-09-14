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
@Table(name = "food")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = false, length = 256)
    private String information;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrivacityEnum privacity = PrivacityEnum.PRIVATE;

    @Column(nullable = true)
    private Boolean isFavorite = false;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sourceOfOrigin;
    
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
