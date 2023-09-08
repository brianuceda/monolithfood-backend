package pe.edu.upc.MonolithFoodApplication.entities;

// Naydeline

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "users_config")

public class UserConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Boolean notifications;
    
    @OneToOne
    @MapsId("user_id")
    private UserEntity user;


}
