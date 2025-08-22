package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "internet_bills")
public class InternetBills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "account_no", nullable = true)
    private String accountNo;


    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;


    @Column(nullable = true)
    private LocalDate date;


    @Column(name = "payment_mode", nullable = true)
    private String paymentMode;


    @Column(nullable = true)
    private String month;


    @Column(nullable = true)
    private BigDecimal amount;

    @Column(nullable = true)
    private BigDecimal payment;


    @Column(length = 1000)
    private String remarks;


    @Column(name = "document_path", length = 1000)
    private String documentPath;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }


    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }


    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }


    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }


    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }


    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getPayment() { return payment; }
    public void setPayment(BigDecimal payment) { this.payment = payment; }


    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }


    public String getDocumentPath() { return documentPath; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }
}

