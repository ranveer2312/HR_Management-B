package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.model.Training;
import com.example.storemanagementbackend.service.TrainingService;
import com.example.storemanagementbackend.service.EmployeeService;
import com.example.storemanagementbackend.service.TrainingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private TrainingAssignmentService trainingAssignmentService;

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training createdTraining = trainingService.createTraining(training);
        return new ResponseEntity<>(createdTraining, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> trainings = trainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id) {
        Optional<Training> training = trainingService.getTrainingById(id);
        if (training.isPresent()) {
            return ResponseEntity.ok(training.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Training>> getTrainingsByEmployeeId(@PathVariable String employeeId) {
        List<Training> trainings = trainingAssignmentService.getTrainingsByEmployeeId(employeeId);
        return ResponseEntity.ok(trainings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody Training training) {
        try {
            Training updatedTraining = trainingService.updateTraining(id, training);
            return ResponseEntity.ok(updatedTraining);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        try {
            trainingService.deleteTraining(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{trainingId}/notify")
    public ResponseEntity<?> sendTrainingToEmployees(
            @PathVariable Long trainingId,
            @RequestBody Map<String, List<String>> request) {
        try {
            List<String> employeeIds = request.get("employeeIds");
            trainingAssignmentService.assignTrainingToEmployees(trainingId, employeeIds);
            return ResponseEntity.ok("Training sent to " + employeeIds.size() + " employees");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send training");
        }
    }

}
