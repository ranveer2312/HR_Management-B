package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "travels")
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = true)
    private String vendor;


    @Column(name = "from_date", nullable = true)
    private LocalDate fromDate;


    @Column(name = "to_date", nullable = true)
    private LocalDate toDate;


    @Column(name = "no_of_days", nullable = true)
    private Integer noOfDays;


    @Column(nullable = true)
    private BigDecimal amount;

    @Column(name = "advance_pay", nullable = true)
    private BigDecimal advancePay;


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = true)
    private PaymentMode paymentMode;


    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;


    @Column(length = 1000)
    private String remarks;


    @Column(name = "document_path", length = 500)
    private String documentPath;

    @Column(nullable = true)
    private LocalDate date;


    public enum PaymentMode {
        UPI, CASH, BANK_TRANSFER, CHEQUE, CARD
    }


    // Getters
    public Long getId() {
        return id;
    }


    public String getVendor() {
        return vendor;
    }


    public LocalDate getFromDate() {
        return fromDate;
    }


    public LocalDate getToDate() {
        return toDate;
    }


    public Integer getNoOfDays() {
        return noOfDays;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getAdvancePay() {
        return advancePay;
    }


    public PaymentMode getPaymentMode() {
        return paymentMode;
    }


    public LocalDate getPaymentDate() {
        return paymentDate;
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


    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }


    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }


    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAdvancePay(BigDecimal advancePay) {
        this.advancePay = advancePay;
    }


    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }


    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
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

