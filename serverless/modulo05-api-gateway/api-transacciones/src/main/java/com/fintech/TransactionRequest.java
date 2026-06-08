package com.fintech;

public class TransactionRequest {
    private String sourceAccount;
    private String destinationAccount;
    private double amount;

    public TransactionRequest() {}

    public TransactionRequest(String sourceAccount, String destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public String getSourceAccount() { return sourceAccount; }
    public void setSourceAccount(String sourceAccount) { this.sourceAccount = sourceAccount; }

    public String getDestinationAccount() { return destinationAccount; }
    public void setDestinationAccount(String destinationAccount) { this.destinationAccount = destinationAccount; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}