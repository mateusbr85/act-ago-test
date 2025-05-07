package com.example.api_act_orders.adapters.outputs.client.productService;

import com.example.api_act_orders.domain.exception.ProductClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductClientErrorDecoder implements ErrorDecoder {


    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        log.error("Erro ao chamar o serviço de produtos. Status: {}", status);
        return switch (status) {
            case 404 -> new ProductClientException(
                    "Produto não encontrado (status 404)",
                    ProductClientException.Reason.NOT_FOUND
            );
            case 401 -> new ProductClientException(
                    "Acesso não autorizado (status 401)",
                    ProductClientException.Reason.UNAUTHORIZED
            );
            default -> new ProductClientException(
                    "Erro inesperado do serviço de produtos (status " + status + ")",
                    ProductClientException.Reason.UNKNOWN
            );
        };
    }
}
