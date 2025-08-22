package com.example.storemanagementbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String partNumber;
    private String productNumber;
    private LocalDate returnDate;
    private Integer quantity;
    private String type; // 'in' or 'out'
    private LocalDate collectDate;
    private String personName;
    private String department;
    private String remarks;
} 