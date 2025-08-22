package com.example.storemanagementbackend.service.impl;

import com.example.storemanagementbackend.model.TrainingAssignment;
import com.example.storemanagementbackend.model.Training;
import com.example.storemanagementbackend.repository.TrainingAssignmentRepository;
import com.example.storemanagementbackend.service.TrainingAssignmentService;
import com.example.storemanagementbackend.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingAssignmentServiceImpl implements TrainingAssignmentService {

    @Autowired
    private TrainingAssignmentRepository trainingAssignmentRepository;

    @Autowired
    private TrainingService trainingService;

    @Override
    public void assignTrainingToEmployees(Long trainingId, List<String> employeeIds) {
        for (String employeeId : employeeIds) {
            TrainingAssignment assignment = new TrainingAssignment();
            assignment.setTrainingId(trainingId);
            assignment.setEmployeeId(employeeId);
            trainingAssignmentRepository.save(assignment);
        }
    }

    @Override
    public List<Training> getTrainingsByEmployeeId(String employeeId) {
        List<TrainingAssignment> assignments = trainingAssignmentRepository.findByEmployeeId(employeeId);
        return assignments.stream()
                .map(assignment -> trainingService.getTrainingById(assignment.getTrainingId()).orElse(null))
                .filter(training -> training != null)
                .collect(Collectors.toList());
    }
}