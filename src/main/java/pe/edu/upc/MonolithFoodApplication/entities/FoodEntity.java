package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Brian | Gabriela | Heather

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer quantity;

    @Column(nullable = false)
    private Boolean isPrivate = false;

    @Column(nullable = false)
    private Boolean isFavorite = false;

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private UserEntity creatorUser;
   
}
