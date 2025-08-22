package com.example.storemanagementbackend.dto;

public class GstCalculationRequest {
    private double amount;
    private double gstRate;
    private String calculationType; // "INCLUSIVE" or "EXCLUSIVE"

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getGstRate() {
        return gstRate;
    }

    public void setGstRate(double gstRate) {
        this.gstRate = gstRate;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }
}

