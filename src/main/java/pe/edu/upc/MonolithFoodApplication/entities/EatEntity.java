package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eat")
public class EatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity food;
    
}
