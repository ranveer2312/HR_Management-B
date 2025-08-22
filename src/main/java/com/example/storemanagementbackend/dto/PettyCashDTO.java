package com.example.storemanagementbackend.dto;

import com.example.storemanagementbackend.model.PettyCash.PaymentMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PettyCashDTO {
    private Long id;
    
    @JsonProperty("item_name")
    private String itemName;
    
    @JsonProperty("paid_to")
    private String paidTo;
    
    @JsonProperty("bill_no")
    private String billNo;
    
    private BigDecimal amount;
    private PaymentMode paymentMode;
    
    @JsonProperty("payment_date")
    private LocalDate paymentDate;
    
    private String remarks;
    
    @JsonProperty("documentpath")
    private String documentPath;

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