package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "subscription_plan")
public class SubscriptionPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

}