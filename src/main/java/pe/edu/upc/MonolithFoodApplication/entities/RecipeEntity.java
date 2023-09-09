package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Heather

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 125)
    private String name = "Receta sin nombre";

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, length = 1000)
    private String preparationGuide;

    @Column(nullable = false, length = 555)
    private String benefits;

    @Column(nullable = false, length = 555)
    private String disadvantages;

    @Column(nullable = false)
    private Boolean isPrivate = false;

    @Column(nullable = false)
    private Boolean isFavorite = false;

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private UserEntity creatorUser;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
	@JoinTable(
		name = "foods_recipes",
		joinColumns = @JoinColumn(name = "recipes_id"),
		inverseJoinColumns = @JoinColumn(name = "food_id")
	)
	private List<FoodEntity> foods;

}
