package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
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

    @OneToMany(mappedBy = "food", fetch = FetchType.EAGER)
    private List<CompositionFoodEntity> compositions;

    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private List<IngredientEntity> ingredients;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<EatEntity> eats;

}
