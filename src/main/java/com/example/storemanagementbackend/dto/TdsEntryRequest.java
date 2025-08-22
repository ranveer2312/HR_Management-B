package com.example.storemanagementbackend.dto;

import java.time.LocalDate;

public class TdsEntryRequest {
    private String tdsFor;
    private Double tdsRate;
    private Double taxableAmount;
    private Double gstRate;
    private Double gstAmount;
    private Double tdsAmount;
    private Double totalPayment;
    private String remarks;
    private String date;

    public String getTdsFor() {
        return tdsFor;
    }

    public void setTdsFor(String tdsFor) {
        this.tdsFor = tdsFor;
    }

    public Double getTdsRate() {
        return tdsRate;
    }

    public void setTdsRate(Double tdsRate) {
        this.tdsRate = tdsRate;
    }

    public Double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(Double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public Double getGstRate() {
        return gstRate;
    }

    public void setGstRate(Double gstRate) {
        this.gstRate = gstRate;
    }

    public Double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(Double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public Double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(Double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}