package com.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
public class FintechApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintechApplication.class, args);
    }

    @Bean
    public Function<Message<TransactionRequest>, Message<Map<String, Object>>> processTransaction() {
        return message -> {
            TransactionRequest request = message.getPayload();

            // Extraer cabecera HTTP de auditoría inyectada por API Gateway
            String auditSource = "NO_PROVISTO";
            if (message.getHeaders().containsKey("x-audit-source")) {
                auditSource = message.getHeaders().get("x-audit-source").toString();
            }

            // Procesar transacción ficticia
            String transactionId = UUID.randomUUID().toString();

            Map<String, Object> body = new HashMap<>();
            body.put("transactionId", transactionId);
            body.put("estado", "APROBADA");
            body.put("origenAuditoria", auditSource);
            body.put("montoProcesado", request.getAmount());

            // Construir respuesta HTTP con headers personalizados
            return MessageBuilder.withPayload(body)
                    .setHeader("statusCode", 201) // Código HTTP de respuesta
                    .setHeader("Content-Type", "application/json")
                    .setHeader("X-Custom-Response-Header", "ProcesadoPorSpring21")
                    .build();
        };
    }
}