package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Willy
@Entity
@Table(name = "objectives")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ObjectivesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @ManyToMany(mappedBy = "objectives")
    private List<UserEntity> users;
}
