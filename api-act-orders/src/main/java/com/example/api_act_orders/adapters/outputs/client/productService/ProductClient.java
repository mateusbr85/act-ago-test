package com.example.api_act_orders.adapters.outputs.client.productService;

import com.example.api_act_orders.domain.ports.outputs.client.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "${product.service.name}", url = "${product.service.url}", configuration = ProductClientConfig.class)
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable("id") UUID productId);
}
