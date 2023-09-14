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
@Table(name = "recipe")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 128)
    private String name = "Receta sin nombre";

    @Column(nullable = false, length = 256)
    private String information;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String preparationGuide;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String benefits;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String disadvantages;

    @Column(nullable = true)
    private Integer review_stars;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrivacityEnum privacity = PrivacityEnum.PRIVATE;

    @Column(nullable = true)
    private Boolean isFavorite = false;

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private UserEntity creatorUser;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
	@JoinTable(
		name = "food_recipe",
		joinColumns = @JoinColumn(name = "recipe_id"),
		inverseJoinColumns = @JoinColumn(name = "food_id")
	)
	private List<FoodEntity> foods;

}
