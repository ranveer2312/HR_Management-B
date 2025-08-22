package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.model.SkillDevelopmentRequest;
import java.util.List;
import java.util.Optional;

public interface SkillDevelopmentRequestService {
    SkillDevelopmentRequest createRequest(SkillDevelopmentRequest request);
    List<SkillDevelopmentRequest> getAllRequests();
    List<SkillDevelopmentRequest> getRequestsByEmployeeId(String employeeId);
    List<SkillDevelopmentRequest> getRequestsByStatus(String status);
    Optional<SkillDevelopmentRequest> getRequestById(Long id);
    SkillDevelopmentRequest updateRequestStatus(Long id, String status, String hrComments);
}