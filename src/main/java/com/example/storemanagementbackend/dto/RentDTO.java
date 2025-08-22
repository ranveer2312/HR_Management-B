package com.example.storemanagementbackend.dto;


import java.math.BigDecimal;
import java.time.LocalDate; // Keep LocalDate for consistency if directly mapping, or use String for flexibility


public class RentDTO {
    private Long id; // Optional for DTOs in POST requests, but useful for PUT/GET
    private String invoiceNo;
    private String ownerName;
    private BigDecimal amount;
    private String date;
    private String paymentDate;
    private String paymentMode;
    private BigDecimal tds;
    private BigDecimal gstAmount;
    private BigDecimal taxableAmount;
    private BigDecimal finalPayment;
    private String remarks;
    private String description;
    private String documentPath; // Add document path field


    // --- Constructors (Optional, but good practice) ---
    public RentDTO() {
    }


    public RentDTO(Long id, String invoiceNo, String ownerName, BigDecimal amount, String date, String paymentDate, String paymentMode, BigDecimal tds, BigDecimal gstAmount, BigDecimal taxableAmount, BigDecimal finalPayment, String remarks, String description) {
        this.id = id;
        this.invoiceNo = invoiceNo;
        this.ownerName = ownerName;
        this.amount = amount;
        this.date = date;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.tds = tds;
        this.gstAmount = gstAmount;
        this.taxableAmount = taxableAmount;
        this.finalPayment = finalPayment;
        this.remarks = remarks;
        this.description = description;
    }


    // --- Getters ---
    public Long getId() {
        return id;
    }


    public String getInvoiceNo() {
        return invoiceNo;
    }


    public String getOwnerName() {
        return ownerName;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public String getDate() {
        return date;
    }


    public String getPaymentDate() {
        return paymentDate;
    }


    public String getPaymentMode() {
        return paymentMode;
    }


    public BigDecimal getTds() {
        return tds;
    }


    public BigDecimal getGstAmount() {
        return gstAmount;
    }


    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }


    public BigDecimal getFinalPayment() {
        return finalPayment;
    }


    public String getRemarks() {
        return remarks;
    }


    public String getDescription() {
        return description;
    }


    public String getDocumentPath() {
        return documentPath;
    }


    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }


    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }


    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }


    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public void setTds(BigDecimal tds) {
        this.tds = tds;
    }


    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }


    public void setTaxableAmount(BigDecimal taxableAmount) {
        this.taxableAmount = taxableAmount;
    }


    public void setFinalPayment(BigDecimal finalPayment) {
        this.finalPayment = finalPayment;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}

