package com.example.storemanagementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabInventoryTransactionDto {
    private Long id;
    private String item;
    private String category;
    private String type;
     private String productNumber;
    private Integer quantity;
    private String location;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private String notes;
} 