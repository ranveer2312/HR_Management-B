package com.example.storemanagementbackend.repository;

import com.example.storemanagementbackend.model.SkillDevelopmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkillDevelopmentRequestRepository extends JpaRepository<SkillDevelopmentRequest, Long> {
    List<SkillDevelopmentRequest> findByEmployeeId(String employeeId);
    List<SkillDevelopmentRequest> findByStatus(String status);
}