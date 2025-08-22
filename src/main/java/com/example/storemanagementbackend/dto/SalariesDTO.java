package com.example.storemanagementbackend.dto;


import java.math.BigDecimal;
import java.time.LocalDate;


public class SalariesDTO {
    private Long id;
    private String empName;
    private String empId;
    private String reimbursement;
    private BigDecimal amount;
    private LocalDate date;
    private String remarks;
    private String documentPath;


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

