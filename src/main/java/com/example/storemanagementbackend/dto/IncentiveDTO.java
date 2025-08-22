package com.example.storemanagementbackend.dto;


import com.example.storemanagementbackend.model.Incentive.PaymentMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;


public class IncentiveDTO {
    private Long id;
    private BigDecimal fixedTarget;
    private BigDecimal achieved;
    private String recipient;
    private LocalDate date;
    private BigDecimal pending;
    private BigDecimal payment;
    @JsonProperty("payment_mode")
    private PaymentMode paymentMode;
    private String remarks;
    private String documentPath;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public BigDecimal getFixedTarget() {
        return fixedTarget;
    }


    public void setFixedTarget(BigDecimal fixedTarget) {
        this.fixedTarget = fixedTarget;
    }


    public BigDecimal getAchieved() {
        return achieved;
    }


    public void setAchieved(BigDecimal achieved) {
        this.achieved = achieved;
    }


    public String getRecipient() {
        return recipient;
    }


    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    public BigDecimal getPending() {
        return pending;
    }


    public void setPending(BigDecimal pending) {
        this.pending = pending;
    }


    public BigDecimal getPayment() {
        return payment;
    }


    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }


    public PaymentMode getPaymentMode() {
        return paymentMode;
    }


    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }


    public String getRemarks() {
        return remarks;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getDocumentPath() {
        return documentPath;
    }


    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}

