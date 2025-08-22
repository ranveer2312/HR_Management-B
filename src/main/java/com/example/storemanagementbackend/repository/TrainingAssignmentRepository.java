package com.example.storemanagementbackend.repository;

import com.example.storemanagementbackend.model.TrainingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainingAssignmentRepository extends JpaRepository<TrainingAssignment, Long> {
    List<TrainingAssignment> findByEmployeeId(String employeeId);
    List<TrainingAssignment> findByTrainingId(Long trainingId);
}