package com.example.storemanagementbackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "petty_cash")
public class PettyCash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "paid_to", nullable = false)
    private String paidTo;

    @Column(name = "bill_no", nullable = false)
    private String billNo;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false)
    private PaymentMode paymentMode;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(length = 1000)
    private String remarks;

    @Column(name = "document_path", length = 500)
    private String documentPath;

    public enum PaymentMode {
        CASH, UPI, BANK_TRANSFER, CARD, CHEQUE
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public String getBillNo() {
        return billNo;
    }

    public BigDecimal getAmount() {
        return amount;
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

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}