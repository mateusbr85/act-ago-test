package com.example.products_service.domain.service;

import com.example.products_service.domain.entity.ProductEntity;
import com.example.products_service.domain.ports.inputs.service.IProductService;
import com.example.products_service.domain.ports.outputs.repositories.IProductRepository;
import com.example.products_service.domain.record.CreateOrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public ProductEntity createOrder(CreateOrderRecord createOrderRecord) {
        ProductEntity product = new ProductEntity();
        product.setName(createOrderRecord.name());
        product.setValue(createOrderRecord.value());

        return this.productRepository.save(product);
    }

    @Override
    public Page<ProductEntity> list(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.productRepository.findAll(pageable);
    }

    @Override
    public ProductEntity getById(UUID id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
}
