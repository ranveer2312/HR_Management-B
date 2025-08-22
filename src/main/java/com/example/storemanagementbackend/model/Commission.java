package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "commissions")
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private BigDecimal fixedTarget;


    @Column(nullable = false)
    private String recipient;


    @Column(nullable = false)
    private BigDecimal achieved;


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


    @Column(nullable = false)
    private LocalDate date;


    public enum PaymentMode {
        UPI, BANK_TRANSFER, CASH, CARD, CHEQUE
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public BigDecimal getFixedTarget() { return fixedTarget; }
    public void setFixedTarget(BigDecimal fixedTarget) { this.fixedTarget = fixedTarget; }


    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }


    public BigDecimal getAchieved() { return achieved; }
    public void setAchieved(BigDecimal achieved) { this.achieved = achieved; }


    public BigDecimal getPending() { return pending; }
    public void setPending(BigDecimal pending) { this.pending = pending; }


    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getPayment() { return payment; }
    public void setPayment(BigDecimal payment) { this.payment = payment; }


    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }


    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }


    public String getDocumentPath() {
        return documentPath;
    }
    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }


    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}

