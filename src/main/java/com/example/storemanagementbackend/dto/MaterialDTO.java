package com.example.storemanagementbackend.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MaterialDTO {
    private Long id;
    private String name;
    private String partNumber;
    private LocalDate returnDate;
    private Integer quantity;
    private String type; // 'in' or 'out'
    private LocalDate collectDate;
    private String personName;
    private String department;
     private String productNumber;
    private String remarks;
} 