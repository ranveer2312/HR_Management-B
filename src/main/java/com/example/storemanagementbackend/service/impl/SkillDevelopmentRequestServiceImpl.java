package com.example.storemanagementbackend.service.impl;

import com.example.storemanagementbackend.model.SkillDevelopmentRequest;
import com.example.storemanagementbackend.repository.SkillDevelopmentRequestRepository;
import com.example.storemanagementbackend.service.SkillDevelopmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SkillDevelopmentRequestServiceImpl implements SkillDevelopmentRequestService {

    @Autowired
    private SkillDevelopmentRequestRepository repository;

    @Override
    public SkillDevelopmentRequest createRequest(SkillDevelopmentRequest request) {
        request.setRequestDate(LocalDateTime.now());
        request.setStatus("PENDING");
        return repository.save(request);
    }

    @Override
    public List<SkillDevelopmentRequest> getAllRequests() {
        return repository.findAll();
    }

    @Override
    public List<SkillDevelopmentRequest> getRequestsByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public List<SkillDevelopmentRequest> getRequestsByStatus(String status) {
        return repository.findByStatus(status);
    }

    @Override
    public Optional<SkillDevelopmentRequest> getRequestById(Long id) {
        return repository.findById(id);
    }

    @Override
    public SkillDevelopmentRequest updateRequestStatus(Long id, String status, String hrComments) {
        Optional<SkillDevelopmentRequest> requestOpt = repository.findById(id);
        if (requestOpt.isPresent()) {
            SkillDevelopmentRequest request = requestOpt.get();
            request.setStatus(status);
            request.setHrComments(hrComments);
            request.setResponseDate(LocalDateTime.now());
            return repository.save(request);
        }
        throw new RuntimeException("Request not found");
    }
}