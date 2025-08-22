package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "electric_bills")
public class ElectricBills {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "account_no", nullable = false)
    private String accountNo;


    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;


    @Column(name = "payment_mode", nullable = false)
    private String paymentMode;


    @Column(name = "bill_type", nullable = false)
    private String billType;


    @Column(name = "month", nullable = false)
    private String month;


    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payment", nullable = false)
    private BigDecimal payment;


    @Column(name = "remarks")
    private String remarks;


    @Column(name = "document_path")
    private String documentPath;


    @Column(name = "date", nullable = false)
    private LocalDate date;


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

