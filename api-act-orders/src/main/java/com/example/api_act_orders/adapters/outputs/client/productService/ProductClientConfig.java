package com.example.api_act_orders.adapters.outputs.client.productService;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class ProductClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ProductClientErrorDecoder();
    }

}
