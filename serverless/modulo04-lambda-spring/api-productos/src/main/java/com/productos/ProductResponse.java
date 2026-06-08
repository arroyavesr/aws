package com.productos;

import java.time.LocalDateTime;

public class ProductResponse {
    private String productId;
    private String name;
    private double finalPrice;
    private String status;
    private String processedTime;

    public ProductResponse() {}

    public ProductResponse(String productId, String name, double finalPrice, String status, String processedTime) {
        this.productId = productId;
        this.name = name;
        this.finalPrice = finalPrice;
        this.status = status;
        this.processedTime = processedTime;
    }

    // Getters y Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getProcessedTime() { return processedTime; }
    public void setProcessedTime(String processedTime) { this.processedTime = processedTime; }
}