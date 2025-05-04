package com.example.api_act_orders.domain.service;

import com.example.api_act_orders.adapters.outputs.client.ProductClient;
import com.example.api_act_orders.adapters.outputs.kafka.OrderEventPublisher;
import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.record.CreateOrderStatus;
import com.example.api_act_orders.domain.enums.StatusEnum;
import com.example.api_act_orders.domain.exception.ProductClientException;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderService;
import com.example.api_act_orders.domain.ports.inputs.request.CreateOrderRequest;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderStatusService;
import com.example.api_act_orders.domain.ports.outputs.client.response.ProductResponse;
import com.example.api_act_orders.domain.ports.outputs.repositories.IOrderRepository;

import com.example.api_act_orders.domain.record.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private IOrderStatusService orderStatusService;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @Override
    public OrderEntity createOrder(CreateOrderRequest createOrderRequest) {
        try{
            ProductResponse productResponse = this.fetchProductOrThrow(createOrderRequest.productId());

            OrderEntity order = new OrderEntity();
            order.setProductId(createOrderRequest.productId());
            order.setTotal(productResponse.price());
            OrderEntity orderSave = this.orderRepository.save(order);

            CreateOrderStatus orderStatus = new CreateOrderStatus(StatusEnum.PENDENTE);
            this.orderStatusService.create(orderStatus, orderSave);

            OrderCreatedEvent event = new OrderCreatedEvent(orderSave.getId(),productResponse.id(),productResponse.price());
            this.orderEventPublisher.publish(event);

            return orderSave;

        }catch (HttpClientErrorException err){
            throw new HttpClientErrorException(HttpStatusCode.valueOf(422));
        }
    }

    @Override
    public Page<OrderEntity> listOrderByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.orderRepository.findAll(pageable);
    }

    @Override
    public OrderEntity getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    private ProductResponse fetchProductOrThrow(UUID productId) {
        try {
            return this.productClient.getProductById(productId);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductClientException("Produto não encontrado: " + productId, ProductClientException.Reason.NOT_FOUND);
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new ProductClientException("Acesso não autorizado ao serviço de produto", ProductClientException.Reason.UNAUTHORIZED);
            } else {
                throw new ProductClientException("Erro desconhecido no serviço de produto", ProductClientException.Reason.UNKNOWN);
            }
        }
    }
}
