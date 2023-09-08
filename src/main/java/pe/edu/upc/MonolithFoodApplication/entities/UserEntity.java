package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Gabriela | Heather | Naydeline | Willy
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String names;
    private String surnames;
    private String profile_img;

     //relation from user to user_personal_info
    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private UserPersonalInfoEntity userPersonalInfo;

     //relation from user_personal_info to objectives 
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "user_objectives",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "objectives_id"),
        uniqueConstraints = {
            @UniqueConstraint
                (
                columnNames = { "user_id", "objectives_id" }
                ) 
            }
        
    )   
    private List<ObjectivesEntity> objectives;
}