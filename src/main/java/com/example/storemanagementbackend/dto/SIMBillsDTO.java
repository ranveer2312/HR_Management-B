package com.example.storemanagementbackend.dto;


import java.math.BigDecimal;
import java.time.LocalDate;


public class SIMBillsDTO {
    private Long id;
    private String accountNo;
    private LocalDate paymentDate;
    private String paymentMode;
    private String month;
    private BigDecimal payment;
    private String remarks;
    private String documentPath;
    private LocalDate date;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getAccountNo() {
        return accountNo;
    }


    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    public LocalDate getPaymentDate() {
        return paymentDate;
    }


    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }


    public String getPaymentMode() {
        return paymentMode;
    }


    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public String getMonth() {
        return month;
    }


    public void setMonth(String month) {
        this.month = month;
    }


    public BigDecimal getPayment() {
        return payment;
    }


    public void setPayment(BigDecimal payment) {
        this.payment = payment;
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


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }
}

