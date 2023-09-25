package pe.edu.upc.MonolithFoodApplication.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "user_config")
public class UserConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean notificationsEnabled = true;

    @Column(nullable = false)
    private Timestamp lastFoodEntry;

    @Column(nullable = false)
    private Timestamp lastWeightUpdate;

    @Column(nullable = false)
    private Boolean darkMode = true;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

}
