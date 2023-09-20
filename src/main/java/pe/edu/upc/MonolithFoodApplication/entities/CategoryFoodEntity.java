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
@Table(name = "category_food")
public class CategoryFoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = true, length = 256)
    private String information;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String benefits;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String disadvantages;

    @OneToMany(
        mappedBy = "category",
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    private List<FoodEntity> foods;

}
