package com.example.storemanagementbackend.dto;


import java.math.BigDecimal;
import java.time.LocalDate;


public class ElectricBillsDTO {
    private Long id;
    private String accountNo;
    private LocalDate paymentDate;
    private String paymentMode;
    private String billType;
    private String month;
    private BigDecimal amount;
    private BigDecimal payment;
    private String remarks;
    private String documentPath;
    private LocalDate date;


    // Constructors
    public ElectricBillsDTO() {
        // Default constructor
    }


    public ElectricBillsDTO(Long id, String accountNo, LocalDate paymentDate, String paymentMode,
                            String billType, String month, BigDecimal amount, BigDecimal payment, String remarks,
                            String documentPath, LocalDate date) {
        this.id = id;
        this.accountNo = accountNo;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.billType = billType;
        this.month = month;
        this.amount = amount;
        this.payment = payment;
        this.remarks = remarks;
        this.documentPath = documentPath;
        this.date = date;
    }


    // Getters
    public Long getId() { return id; }
    public String getAccountNo() { return accountNo; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getPaymentMode() { return paymentMode; }
    public String getBillType() { return billType; }
    public String getMonth() { return month; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getPayment() { return payment; }
    public String getRemarks() { return remarks; }
    public String getDocumentPath() { return documentPath; }
    public LocalDate getDate() { return date; }


    // Setters
    public void setId(Long id) { this.id = id; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public void setBillType(String billType) { this.billType = billType; }
    public void setMonth(String month) { this.month = month; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPayment(BigDecimal payment) { this.payment = payment; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }
    public void setDate(LocalDate date) { this.date = date; }
}

