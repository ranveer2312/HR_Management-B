package com.example.storemanagementbackend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tds_entries")
public class TdsEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tdsFor;
    private double tdsRate;
    private double taxableAmount;
    private double amount;
    private double gstRate;
    private double gstAmount;
    private double tdsAmount;
    private double totalPayment;
    private String remarks;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTdsFor() {
        return tdsFor;
    }

    public void setTdsFor(String tdsFor) {
        this.tdsFor = tdsFor;
    }

    public double getTdsRate() {
        return tdsRate;
    }

    public void setTdsRate(double tdsRate) {
        this.tdsRate = tdsRate;
    }

    public double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getGstRate() {
        return gstRate;
    }

    public void setGstRate(double gstRate) {
        this.gstRate = gstRate;
    }

    public double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}


