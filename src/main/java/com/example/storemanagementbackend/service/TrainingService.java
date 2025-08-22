package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.model.Training;
import java.util.List;
import java.util.Optional;

public interface TrainingService {
    Training createTraining(Training training);
    List<Training> getAllTrainings();
    Optional<Training> getTrainingById(Long id);
    Training updateTraining(Long id, Training training);
    void deleteTraining(Long id);
}