package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40, unique = true)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 128)
    private String names;

    @Column(nullable = false, length = 128)
    private String surnames;

    @Column(nullable = false, length = 512)
    private String profileImg;

    @Column(nullable = false)
    @Builder.Default
    private Boolean is_account_blocked = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserConfigEntity userConfig;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IpLoginAttemptEntity> ipLoginAttempt;
  
    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private UserPersonalInfoEntity userPersonalInfo;
  
    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private UserFitnessInfoEntity userFitnessInfo;

    @ManyToMany(
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "user_objective",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "objective_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {
                "user_id",
                "objective_id"
            })
        }
    )
    private List<ObjectiveEntity> objectives;
    
    @OneToMany(mappedBy = "creatorUser", cascade = CascadeType.ALL)
    private List<RecipeEntity> createdRecipes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EatEntity> eats;

    @ManyToMany(
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name ="user_role",
        joinColumns = @JoinColumn (name = "user_id"),
        inverseJoinColumns = @JoinColumn (name = "role_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {
                "user_id",
                "role_id"
            })
        }
    )
    private Set<RoleEntity> roles;

}
