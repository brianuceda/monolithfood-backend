package pe.edu.upc.MonolithFoodApplication.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

// Gabriela | Heather | Naydeline | Willy


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, length = 120, unique = true)
    @NotBlank
    @Email
    private String username;

    @Column (nullable = false, length= 80)
    @NotBlank
    private String password;

    @Column (length = 100)
    @NotBlank
    private String names;

    @Column (length =100)
    @NotBlank
    private String surnames;

    @Column (length = 400)
    private String profile_img;


    @OneToOne (mappedBy = "userEntity", cascade = CascadeType.ALL)
    private UserConfigEntity userConfigEntity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name ="user_roles",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "role_id")
    )
    private List<RoleEntity> roles;

}