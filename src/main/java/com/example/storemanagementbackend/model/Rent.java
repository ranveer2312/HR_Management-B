package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "rent") // Assuming your table name is 'rent'
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true) // Invoice number is typically unique and not null
    private String invoiceNo;


    @Column(nullable = false)
    private String ownerName;


    @Column(nullable = false)
    private BigDecimal amount; // Original rent amount


    @Column(nullable = false)
    private LocalDate date; // Date of the expense/invoice date


    @Column(nullable = false)
    private LocalDate paymentDate; // Date when the payment was made


    @Column(nullable = false)
    private String paymentMode; // e.g., "Cash", "Bank Transfer", "UPI", "Cheque"


    @Column(nullable = true) // TDS might not always apply
    private BigDecimal tds;


    @Column(nullable = true) // GST might not always apply
    private BigDecimal gstAmount;


    @Column(nullable = true) // Taxable amount might not always be calculated/present
    private BigDecimal taxableAmount;


    @Column(nullable = false)
    private BigDecimal payment;

    @Column(nullable = false)
    private BigDecimal finalPayment; // The actual amount paid after deductions/additions


    @Column(length = 500) // Remarks can be longer
    private String remarks;


    @Column(length = 255) // Keep description concise as per frontend (max 6 words)
    private String description;


    @Column(length = 500) // Document path for uploaded files
    private String documentPath;


    // --- Constructors (Optional, but good practice) ---
    public Rent() {
    }


    public Rent(String invoiceNo, String ownerName, BigDecimal amount, LocalDate date, LocalDate paymentDate, String paymentMode, BigDecimal tds, BigDecimal gstAmount, BigDecimal taxableAmount, BigDecimal finalPayment, String remarks, String description) {
        this.invoiceNo = invoiceNo;
        this.ownerName = ownerName;
        this.amount = amount;
        this.date = date;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.tds = tds;
        this.gstAmount = gstAmount;
        this.taxableAmount = taxableAmount;
        this.finalPayment = finalPayment;
        this.remarks = remarks;
        this.description = description;
    }


    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getInvoiceNo() {
        return invoiceNo;
    }


    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }


    public String getOwnerName() {
        return ownerName;
    }


    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
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


    public BigDecimal getTds() {
        return tds;
    }


    public void setTds(BigDecimal tds) {
        this.tds = tds;
    }


    public BigDecimal getGstAmount() {
        return gstAmount;
    }


    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }


    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }


    public void setTaxableAmount(BigDecimal taxableAmount) {
        this.taxableAmount = taxableAmount;
    }


    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getFinalPayment() {
        return finalPayment;
    }


    public void setFinalPayment(BigDecimal finalPayment) {
        this.finalPayment = finalPayment;
    }


    public String getRemarks() {
        return remarks;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getDocumentPath() {
        return documentPath;
    }


    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }


    @Override
    public String toString() {
        return "Rent{" +
               "id=" + id +
               ", invoiceNo='" + invoiceNo + '\'' +
               ", ownerName='" + ownerName + '\'' +
               ", amount=" + amount +
               ", date=" + date +
               ", paymentDate=" + paymentDate +
               ", paymentMode='" + paymentMode + '\'' +
               ", tds=" + tds +
               ", gstAmount=" + gstAmount +
               ", taxableAmount=" + taxableAmount +
               ", finalPayment=" + finalPayment +
               ", remarks='" + remarks + '\'' +
               ", description='" + description + '\'' +
               ", documentPath='" + documentPath + '\'' +
               '}';
    }
}

