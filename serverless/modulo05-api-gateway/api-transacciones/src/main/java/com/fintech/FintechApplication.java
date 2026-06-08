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
            Map<String, Object> body = new HashMap<>();

            // Extraer headers de auditoría
            String auditSource = message.getHeaders().getOrDefault("x-audit-source", "NO_PROVISTO").toString();

            // Extraer query string parameters inyectados por API Gateway en el Message Header
            // API Gateway HttpApi v2 introduce los query parameters en una cabecera u objeto mapa.
            // Spring mapea estas variables en la cabecera "queryParameters".
            boolean forceReject = false;
            if (message.getHeaders().containsKey("queryParameters")) {
                Map<?, ?> queryParams = (Map<?, ?>) message.getHeaders().get("queryParameters");
                if (queryParams != null && queryParams.containsKey("forceReject")) {
                    forceReject = Boolean.parseBoolean(queryParams.get("forceReject").toString());
                }
            }

            if (forceReject) {
                body.put("estado", "RECHAZADA");
                body.put("motivo", "Transacción forzada a rechazo");
                return MessageBuilder.withPayload(body)
                        .setHeader("statusCode", 400)
                        .setHeader("Content-Type", "application/json")
                        .build();
            }

            String transactionId = UUID.randomUUID().toString();
            body.put("transactionId", transactionId);
            body.put("estado", "APROBADA");
            body.put("origenAuditoria", auditSource);
            body.put("montoProcesado", request.getAmount());

            return MessageBuilder.withPayload(body)
                    .setHeader("statusCode", 201)
                    .setHeader("Content-Type", "application/json")
                    .setHeader("X-Custom-Response-Header", "ProcesadoPorSpring21")
                    .build();
        };
    }
}