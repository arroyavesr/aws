package com.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    /**
     * Bean de Spring Cloud Function.
     * Jackson serializará y deserializará automáticamente las clases ProductRequest y ProductResponse.
     */
    @Bean
    public Function<ProductRequest, ProductResponse> processProduct() {
        return request -> {
            if (request == null || request.getName() == null || request.getName().isEmpty()) {
                throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
            }
            if (request.getPrice() <= 0) {
                throw new IllegalArgumentException("El precio del producto debe ser mayor a cero");
            }

            double tax = 0.19; // 19% IVA
            double finalPrice = request.getPrice() + (request.getPrice() * tax);
            String generatedId = UUID.randomUUID().toString();
            String timestamp = LocalDateTime.now().toString();

            return new ProductResponse(
                    generatedId,
                    request.getName().toUpperCase(),
                    finalPrice,
                    "PROCESADO_EXITOSAMENTE",
                    timestamp
            );
        };
    }
}