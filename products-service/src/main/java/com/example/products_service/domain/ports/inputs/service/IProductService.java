package com.example.products_service.domain.ports.inputs.service;

import com.example.products_service.domain.entity.ProductEntity;
import com.example.products_service.domain.record.CreateOrderRecord;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IProductService {
    ProductEntity createOrder(CreateOrderRecord createOrderRecord);
    Page<ProductEntity> list(int page, int size);
    ProductEntity getById(UUID id);
}
