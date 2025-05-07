package com.example.api_act_orders.domain.service;

import com.example.api_act_orders.adapters.outputs.client.productService.ProductClient;
import com.example.api_act_orders.adapters.outputs.kafka.PaymentEventPublisher;
import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.entity.OrderStatusEntity;
import com.example.api_act_orders.domain.record.CreateOrderStatus;
import com.example.api_act_orders.domain.enums.StatusEnum;
import com.example.api_act_orders.domain.exception.ProductClientException;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderService;
import com.example.api_act_orders.domain.ports.inputs.request.CreateOrderRequest;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderStatusService;
import com.example.api_act_orders.domain.ports.outputs.client.response.ProductResponse;
import com.example.api_act_orders.domain.ports.outputs.repositories.IOrderRepository;

import com.example.api_act_orders.domain.record.PaymentCreatedEvent;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;

    private final ProductClient productClient;

    private final IOrderStatusService orderStatusService;

    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    public OrderEntity createOrder(CreateOrderRequest createOrderRequest) {
        try{
            ProductResponse productResponse = this.fetchProductOrThrow(createOrderRequest.productId());

            OrderEntity order = new OrderEntity();
            order.setProductId(createOrderRequest.productId());
            order.setTotal(productResponse.value());
            OrderEntity orderSave = this.orderRepository.save(order);

            CreateOrderStatus orderStatus = new CreateOrderStatus(StatusEnum.PENDENTE);
            this.orderStatusService.create(orderStatus, orderSave);

            PaymentCreatedEvent event = new PaymentCreatedEvent(orderSave.getId());
            this.paymentEventPublisher.publish(event);

            return orderSave;

        }catch (ProductClientException e) {
            log.error("Erro ao validar o prodtuo: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao criar pedido: {}", e.getMessage(), e);
            throw new RuntimeException(e);
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
            return productClient.getProductById(productId); // corrige o uso de id para productId
        } catch (FeignException e) {
            log.error("Erro de comunicação com o serviço de produto: {}", e.getMessage(), ProductClientException.Reason.CONNECTION_ERROR);
            throw new ProductClientException("Erro de comunicação com o serviço de produto: " + e.getMessage(), ProductClientException.Reason.CONNECTION_ERROR);
        } catch (ProductClientException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar produto: {}", e.getMessage());
            throw new ProductClientException("Erro desconhecido ao buscar produto: " + e.getMessage(), ProductClientException.Reason.UNKNOWN);
        }
    }
}
