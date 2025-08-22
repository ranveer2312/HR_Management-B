package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.model.TrainingAssignment;
import com.example.storemanagementbackend.model.Training;
import java.util.List;

public interface TrainingAssignmentService {
    void assignTrainingToEmployees(Long trainingId, List<String> employeeIds);
    List<Training> getTrainingsByEmployeeId(String employeeId);
}