package com.example.storemanagementbackend.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceImprovementPlanDTO {
    private Long id;
    private String employeeId;
    private String planStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private String objectives;
    private String actions;
    private String support;
    private LocalDate reviewDate;
    private String reviewer;
    private String comments;
    private String goalSetting;
}
 