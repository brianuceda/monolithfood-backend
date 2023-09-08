package pe.edu.upc.MonolithFoodApplication.entities;

// Heather
import jakarta.persistence.*;
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class RecipeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  
  private String description;

  private String preparationGuide;
  
  private String benefits;
  
  private String disadvantages;

  private Boolean isPrivate;

  private Boolean isFavorite;

  @ManyToOne
  @JoinColumn(name = "creator_user_id")
  private UserEntity creatorUser;

  @ManyToMany(mappedBy = "recipes")
  private List<FoodEntity> foods;
  
}
