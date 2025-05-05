package com.example.products_service.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private UUID id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_value")
    private BigDecimal value;

    @Column(name = "product_created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
