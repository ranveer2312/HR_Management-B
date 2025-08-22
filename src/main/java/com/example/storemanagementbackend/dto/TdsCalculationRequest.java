package com.example.storemanagementbackend.dto;

public class TdsCalculationRequest {
    private Double taxableAmount;
    private String category;
    private Double tdsRate;
    private Double gstRate;

    public Double getTaxableAmount() { return taxableAmount; }
    public void setTaxableAmount(Double taxableAmount) { this.taxableAmount = taxableAmount; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Double getTdsRate() { return tdsRate; }
    public void setTdsRate(Double tdsRate) { this.tdsRate = tdsRate; }
    
    public Double getGstRate() { return gstRate; }
    public void setGstRate(Double gstRate) { this.gstRate = gstRate; }
}