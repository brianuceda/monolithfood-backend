package pe.edu.upc.MonolithFoodApplication.entities;

// Gabriela | Heather | Naydeline | Willy

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String names;
    private String surnames;
    private String profileImg;

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EatEntity> eats;

    @OneToMany(mappedBy = "creatorUser", cascade = CascadeType.ALL)
    private List<FoodEntity> createdFoods;


}