package com.example.storemanagementbackend.dto;


import com.example.storemanagementbackend.model.Travel.PaymentMode;
import java.math.BigDecimal;
import java.time.LocalDate;


public class TravelDTO {
    private Long id;
    private String vendor;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer noOfDays;
    private BigDecimal advancePay;
    private PaymentMode paymentMode;
    private LocalDate paymentDate;
    private String remarks;
    private String documentPath;
    private LocalDate date;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getVendor() {
        return vendor;
    }


    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public LocalDate getFromDate() {
        return fromDate;
    }


    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }


    public LocalDate getToDate() {
        return toDate;
    }


    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }


    public Integer getNoOfDays() {
        return noOfDays;
    }


    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }


    public BigDecimal getAdvancePay() {
        return advancePay;
    }


    public void setAdvancePay(BigDecimal advancePay) {
        this.advancePay = advancePay;
    }


    public PaymentMode getPaymentMode() {
        return paymentMode;
    }


    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }


    public LocalDate getPaymentDate() {
        return paymentDate;
    }


    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
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

