package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.dto.PerformanceImprovementPlanDTO;
import java.util.List;

public interface PerformanceImprovementPlanService {
    PerformanceImprovementPlanDTO createPlan(PerformanceImprovementPlanDTO planDTO);
    List<PerformanceImprovementPlanDTO> getAllPlans();
    PerformanceImprovementPlanDTO getPlanById(Long id);
    List<PerformanceImprovementPlanDTO> getPlansByEmployeeId(String employeeId);
    PerformanceImprovementPlanDTO updatePlan(Long id, PerformanceImprovementPlanDTO planDTO);
    void deletePlan(Long id);
} 