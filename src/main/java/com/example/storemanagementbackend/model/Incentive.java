package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "incentives")
public class Incentive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private BigDecimal fixedTarget;


    @Column(nullable = false)
    private BigDecimal achieved;


    @Column(nullable = false)
    private String recipient;


    @Column(nullable = false)
    private LocalDate date;


    @Column(nullable = false)
    private BigDecimal pending;


    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal payment;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode;


    @Column(length = 1000)
    private String remarks;


    @Column(length = 500)
    private String documentPath;


    public enum PaymentMode {
        UPI, CASH, BANK_TRANSFER, CHEQUE, CARD
    }


    // Getters
    public Long getId() {
        return id;
    }


    public BigDecimal getFixedTarget() {
        return fixedTarget;
    }


    public BigDecimal getAchieved() {
        return achieved;
    }


    public String getRecipient() {
        return recipient;
    }


    public LocalDate getDate() {
        return date;
    }


    public BigDecimal getPending() {
        return pending;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPayment() {
        return payment;
    }


    public PaymentMode getPaymentMode() {
        return paymentMode;
    }


    public String getRemarks() {
        return remarks;
    }


    public String getDocumentPath() {
        return documentPath;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }


    public void setFixedTarget(BigDecimal fixedTarget) {
        this.fixedTarget = fixedTarget;
    }


    public void setAchieved(BigDecimal achieved) {
        this.achieved = achieved;
    }


    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    public void setPending(BigDecimal pending) {
        this.pending = pending;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }


    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}

