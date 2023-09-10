package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Gabriela | Heather | Naydeline | Willy

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String names;

    @Column(nullable = false, length = 100)
    private String surnames;

    @Column(nullable = false, length = 755)
    private String profileImg;
    
    @OneToMany(mappedBy = "creatorUser", cascade = CascadeType.ALL)
    private List<RecipeEntity> createdRecipes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EatEntity> eats;
  
}
