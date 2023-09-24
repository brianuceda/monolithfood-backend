package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double review_stars;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrivacityEnum privacity = PrivacityEnum.PRIVATE;

    @Column(nullable = true)
    private Boolean isFavorite = false;

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private UserEntity creatorUser;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    private List<IngredientEntity> ingredients;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<EatEntity> eats;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    private List<CompositionRecipeEntity> compositions;
}
