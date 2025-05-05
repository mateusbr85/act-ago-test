package com.example.products_service.domain.ports.outputs.repositories;

import com.example.products_service.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<ProductEntity, UUID> {
}
