package com.example.products_service.adapters.inputs.controller;

import com.example.products_service.domain.entity.ProductEntity;
import com.example.products_service.domain.ports.inputs.service.IProductService;
import com.example.products_service.domain.record.CreateOrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(
            @RequestBody CreateOrderRecord createOrderRecord
    ){
        ProductEntity productReturn = this.productService.createOrder(createOrderRecord);
        return ResponseEntity.ok().body(productReturn);
    }

    @GetMapping(value = "/${id}")
    public ResponseEntity<ProductEntity> getProductById(
            @PathVariable UUID id
    ) {
        ProductEntity result = this.productService.getById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<Page<ProductEntity>> listProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductEntity> orders = this.productService.list(page, size);
        return ResponseEntity.ok(orders);
    }

}
