package com.example.storemanagementbackend.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "salaries")
public class Salaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "emp_name", nullable = false)
    private String empName;


    @Column(name = "emp_id", nullable = false)
    private String empId;


    @Column(nullable = false)
    private String reimbursement;


    @Column(nullable = false)
    private BigDecimal amount;


    @Column(nullable = false)
    private LocalDate date;


    @Column(length = 1000)
    private String remarks;


    @Column(name = "document_path")
    private String documentPath;


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }


    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }


    public String getReimbursement() { return reimbursement; }
    public void setReimbursement(String reimbursement) { this.reimbursement = reimbursement; }


    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }


    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }


    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }


    public String getDocumentPath() { return documentPath; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }
}

