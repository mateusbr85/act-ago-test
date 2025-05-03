package com.example.api_act_orders.domain.entity;

import com.example.api_act_orders.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_types")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StatusTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_type_id")
    private Long id;

    @Column(name = "status_type_name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum name;

    @Column(name = "status_type_description")
    private String description;

    @Column(name = "status_type_created_at", nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();
}
