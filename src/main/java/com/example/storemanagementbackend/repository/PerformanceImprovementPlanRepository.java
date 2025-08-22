package com.example.storemanagementbackend.repository;
 
import com.example.storemanagementbackend.model.PerformanceImprovementPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface PerformanceImprovementPlanRepository extends JpaRepository<PerformanceImprovementPlan, Long> {
    List<PerformanceImprovementPlan> findByEmployee_EmployeeId(String employeeId);
}
 