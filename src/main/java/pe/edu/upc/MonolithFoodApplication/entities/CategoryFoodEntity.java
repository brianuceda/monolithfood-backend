package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "category", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    private List<FoodEntity> foods;

}
