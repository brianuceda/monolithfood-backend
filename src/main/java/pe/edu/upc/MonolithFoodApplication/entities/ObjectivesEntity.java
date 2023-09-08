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
    private String name;
    private String description;

    //relation from user_personal_info to objectives
    @ManyToMany(mappedBy = "objectives")
    private List<UserEntity> users;
}
