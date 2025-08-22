package com.example.storemanagementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabInstrumentDto {
    private Long id;
    private String name;
    private String category;
    private Integer quantity;
     private String productNumber;
    private String location;
    private String itemCondition;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastUpdated;
} 