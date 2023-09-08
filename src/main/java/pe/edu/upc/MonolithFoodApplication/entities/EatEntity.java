package pe.edu.upc.MonolithFoodApplication.entities;

// Gabriela
import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eat")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity food;

    private int quantity; 
    private String mealType; 
}
