package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "users", indexes = {
        @Index(name = "user_username_idx", columnList = "username", unique = true),
        @Index(name = "user_email_idx", columnList = "email", unique = true)
})
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40, unique = true)
    private String username;

    @Column(nullable = true, length = 80)
    private String password;

    @Column(nullable = true, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 128)
    private String names;

    @Column(nullable = false, length = 512)
    private String profileImg;

    @Column(nullable = true, length = 64, unique = true)
    private String oauthProviderId;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isOauthRegistered = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAccountBlocked = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<IpLoginAttemptEntity> ipLoginAttempt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserConfigEntity userConfig;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserPersonalInfoEntity userPersonalInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserFitnessInfoEntity userFitnessInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_objective", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "objective_id"), uniqueConstraints = {
            @UniqueConstraint(columnNames = {
                    "user_id",
                    "objective_id"
            })
    })
    private List<ObjectiveEntity> objectives;

    @OneToMany(mappedBy = "creatorUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RecipeEntity> createdRecipes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EatEntity> eats;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = {
            @UniqueConstraint(columnNames = {
                    "user_id",
                    "role_id"
            })
    })
    private Set<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
  
}
