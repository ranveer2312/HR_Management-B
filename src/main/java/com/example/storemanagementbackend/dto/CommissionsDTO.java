package com.example.storemanagementbackend.dto;


import java.math.BigDecimal;
import java.time.LocalDate;


public class CommissionsDTO {
    private Long id;
    private BigDecimal fixedTarget;
    private String recipient;
    private BigDecimal achieved;
    private BigDecimal pending;
    private BigDecimal payment;
    private String paymentMode;
    private String remarks;
    private String documentPath;
    private LocalDate date;


    // Getters
    public Long getId() { return id; }
    public BigDecimal getFixedTarget() { return fixedTarget; }
    public String getRecipient() { return recipient; }
    public BigDecimal getAchieved() { return achieved; }
    public BigDecimal getPending() { return pending; }
    public BigDecimal getPayment() { return payment; }
    public String getPaymentMode() { return paymentMode; }
    public String getRemarks() { return remarks; }
    public String getDocumentPath() {
        return documentPath;
    }
    public LocalDate getDate() { return date; }


    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFixedTarget(BigDecimal fixedTarget) { this.fixedTarget = fixedTarget; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setAchieved(BigDecimal achieved) { this.achieved = achieved; }
    public void setPending(BigDecimal pending) { this.pending = pending; }
    public void setPayment(BigDecimal payment) { this.payment = payment; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
    public void setDate(LocalDate date) { this.date = date; }
}

