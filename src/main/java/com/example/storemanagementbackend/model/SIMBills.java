package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "sim_bills")
public class SIMBills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "account_no", nullable = false)
    private String accountNo;


    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;


    @Column(name = "payment_mode", nullable = false)
    private String paymentMode;


    @Column(nullable = false)
    private String month;


    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal payment;


    @Column(length = 1000)
    private String remarks;


    @Column(name = "document_path")
    private String documentPath;


    @Column(nullable = false)
    private LocalDate date;


    // Getters
    public Long getId() {
        return id;
    }


    public String getAccountNo() {
        return accountNo;
    }


    public LocalDate getPaymentDate() {
        return paymentDate;
    }


    public String getPaymentMode() {
        return paymentMode;
    }


    public String getMonth() {
        return month;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPayment() {
        return payment;
    }


    public String getRemarks() {
        return remarks;
    }


    public String getDocumentPath() {
        return documentPath;
    }


    public LocalDate getDate() {
        return date;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }


    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }


    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public void setMonth(String month) {
        this.month = month;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }
}

