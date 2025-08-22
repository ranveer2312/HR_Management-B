package com.example.storemanagementbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.storemanagementbackend.service.PerformanceImprovementPlanService;
import com.example.storemanagementbackend.dto.PerformanceImprovementPlanDTO;
import com.example.storemanagementbackend.model.PerformanceImprovementPlan;
import com.example.storemanagementbackend.model.Employee;
import com.example.storemanagementbackend.model.PlanStatus;
import com.example.storemanagementbackend.repository.PerformanceImprovementPlanRepository;
import com.example.storemanagementbackend.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceImprovementPlanServiceImpl implements PerformanceImprovementPlanService {
    @Autowired
    private PerformanceImprovementPlanRepository planRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public PerformanceImprovementPlanDTO createPlan(PerformanceImprovementPlanDTO planDTO) {
        Employee employee = employeeRepository.findByEmployeeId(planDTO.getEmployeeId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        PerformanceImprovementPlan plan = PerformanceImprovementPlan.builder()
            .employee(employee)
            .planStatus(PlanStatus.valueOf(planDTO.getPlanStatus()))
            .startDate(planDTO.getStartDate())
            .endDate(planDTO.getEndDate())
            .objectives(planDTO.getObjectives())
            .actions(planDTO.getActions())
            .support(planDTO.getSupport())
            .reviewDate(planDTO.getReviewDate())
            .reviewer(planDTO.getReviewer())
            .comments(planDTO.getComments())
            .goalSetting(planDTO.getGoalSetting())
            .build();
        
        return convertToDTO(planRepository.save(plan));
    }
    
    @Override
    public List<PerformanceImprovementPlanDTO> getAllPlans() {
        return planRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public PerformanceImprovementPlanDTO getPlanById(Long id) {
        return planRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Plan not found"));
    }
    
    @Override
    public List<PerformanceImprovementPlanDTO> getPlansByEmployeeId(String employeeId) {
        return planRepository.findByEmployee_EmployeeId(employeeId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public PerformanceImprovementPlanDTO updatePlan(Long id, PerformanceImprovementPlanDTO planDTO) {
        PerformanceImprovementPlan plan = planRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Plan not found"));
        
        plan.setPlanStatus(PlanStatus.valueOf(planDTO.getPlanStatus()));
        plan.setStartDate(planDTO.getStartDate());
        plan.setEndDate(planDTO.getEndDate());
        plan.setObjectives(planDTO.getObjectives());
        plan.setActions(planDTO.getActions());
        plan.setSupport(planDTO.getSupport());
        plan.setReviewDate(planDTO.getReviewDate());
        plan.setReviewer(planDTO.getReviewer());
        plan.setComments(planDTO.getComments());
        plan.setGoalSetting(planDTO.getGoalSetting());
        
        return convertToDTO(planRepository.save(plan));
    }
    
    @Override
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }
    
    private PerformanceImprovementPlanDTO convertToDTO(PerformanceImprovementPlan plan) {
        return new PerformanceImprovementPlanDTO(
            plan.getId(),
            plan.getEmployee().getEmployeeId(),
            plan.getPlanStatus().toString(),
            plan.getStartDate(),
            plan.getEndDate(),
            plan.getObjectives(),
            plan.getActions(),
            plan.getSupport(),
            plan.getReviewDate(),
            plan.getReviewer(),
            plan.getComments(),
            plan.getGoalSetting()
        );
    }
} 