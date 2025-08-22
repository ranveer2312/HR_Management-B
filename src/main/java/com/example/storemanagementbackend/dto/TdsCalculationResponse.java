package com.example.storemanagementbackend.dto;

public class TdsCalculationResponse {
    private Double taxableAmount;
    private Double tdsRate;
    private Double tdsAmount;
    private Double gstAmount;
    private Double totalPayment;
    private String category;

    public Double getTaxableAmount() { return taxableAmount; }
    public void setTaxableAmount(Double taxableAmount) { this.taxableAmount = taxableAmount; }
    
    public Double getTdsRate() { return tdsRate; }
    public void setTdsRate(Double tdsRate) { this.tdsRate = tdsRate; }
    
    public Double getTdsAmount() { return tdsAmount; }
    public void setTdsAmount(Double tdsAmount) { this.tdsAmount = tdsAmount; }
    
    public Double getGstAmount() { return gstAmount; }
    public void setGstAmount(Double gstAmount) { this.gstAmount = gstAmount; }
    
    public Double getTotalPayment() { return totalPayment; }
    public void setTotalPayment(Double totalPayment) { this.totalPayment = totalPayment; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}