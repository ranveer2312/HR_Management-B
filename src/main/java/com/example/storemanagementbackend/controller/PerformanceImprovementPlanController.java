package com.example.storemanagementbackend.controller;
 
import com.example.storemanagementbackend.dto.PerformanceImprovementPlanDTO;
import com.example.storemanagementbackend.service.PerformanceImprovementPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 
@RestController
@RequestMapping("/api/pips")
public class PerformanceImprovementPlanController {
    @Autowired
    private PerformanceImprovementPlanService planService;
 
    @PostMapping
    public ResponseEntity<PerformanceImprovementPlanDTO> createPlan(@RequestBody PerformanceImprovementPlanDTO dto) {
        return new ResponseEntity<>(planService.createPlan(dto), HttpStatus.CREATED);
    }
 
    @GetMapping
    public ResponseEntity<List<PerformanceImprovementPlanDTO>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<PerformanceImprovementPlanDTO> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }
 
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceImprovementPlanDTO>> getPlansByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(planService.getPlansByEmployeeId(employeeId));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<PerformanceImprovementPlanDTO> updatePlan(@PathVariable Long id, @RequestBody PerformanceImprovementPlanDTO dto) {
        return ResponseEntity.ok(planService.updatePlan(id, dto));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
 
