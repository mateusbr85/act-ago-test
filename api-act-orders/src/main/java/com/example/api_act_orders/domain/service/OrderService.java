package com.example.api_act_orders.domain.service;

import com.example.api_act_orders.adapters.outputs.client.ProductClient;
import com.example.api_act_orders.domain.entity.OrderEntity;
import com.example.api_act_orders.domain.entity.OrderStatusEntity;
import com.example.api_act_orders.domain.exception.ProductClientException;
import com.example.api_act_orders.domain.ports.inputs.service.IOrderService;
import com.example.api_act_orders.domain.ports.inputs.service.request.CreateOrderRequest;
import com.example.api_act_orders.domain.ports.outputs.client.response.ProductResponse;
import com.example.api_act_orders.domain.ports.outputs.repositories.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(IOrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    @Override
    public OrderEntity createOrder(CreateOrderRequest createOrderRequest) {
        try{
//            ProductResponse productResponse = this.fetchProductOrThrow(createOrderRequest.productId());

            OrderEntity order = new OrderEntity();
            order.setProductId(createOrderRequest.productId());
            order.setTotal(BigDecimal.valueOf(100.00));

            return this.orderRepository.save(order);

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
