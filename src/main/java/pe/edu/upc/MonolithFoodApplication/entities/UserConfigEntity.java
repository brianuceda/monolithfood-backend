package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Naydeline

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "users_config")
public class UserConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean notifications = false;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;


}