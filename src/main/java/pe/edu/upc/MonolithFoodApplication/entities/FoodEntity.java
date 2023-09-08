package pe.edu.upc.MonolithFoodApplication.entities;

// Brian | Gabriela | Heather

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "food")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private int quantity;
    private boolean isPrivate;
    private boolean isFavorite;

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private UserEntity creatorUser;
   
}
