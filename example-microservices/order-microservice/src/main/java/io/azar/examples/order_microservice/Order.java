package io.azar.examples.order_microservice;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")  // Renaming the table to "orders"
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private int quantity;
    // Getters and Setters


}

