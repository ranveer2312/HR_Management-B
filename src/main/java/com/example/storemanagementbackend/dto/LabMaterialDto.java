package com.example.storemanagementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabMaterialDto {
    private Long id;
    private String name;
    private String category;
    private Integer quantity;
    private String location;
     private String productNumber;
    private String itemCondition;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastUpdated;
} 