package com.example.api_act_orders.adapters.inputs.controller;

import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderService;
import com.example.api_act_orders.domain.ports.inputs.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping(value = "orders")
@RequiredArgsConstructor
public class OrderController {


    private final IOrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderEntity>> listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<OrderEntity> orders = orderService.listOrderByPage(page, size);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(
            @RequestBody CreateOrderRequest request
    ) {
        OrderEntity createOrder = orderService.createOrder(request);
        return ResponseEntity.ok(createOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(
            @PathVariable UUID id

    ){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

}
