package com.example.storemanagementbackend.service.impl;

import com.example.storemanagementbackend.model.Training;
import com.example.storemanagementbackend.repository.TrainingRepository;
import com.example.storemanagementbackend.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Optional<Training> getTrainingById(Long id) {
        return trainingRepository.findById(id);
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        training.setId(id);
        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}