package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.model.SkillDevelopmentRequest;
import com.example.storemanagementbackend.service.SkillDevelopmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/skill-requests")
public class SkillDevelopmentRequestController {

    @Autowired
    private SkillDevelopmentRequestService skillRequestService;

    @PostMapping
    public ResponseEntity<SkillDevelopmentRequest> createSkillRequest(@RequestBody SkillDevelopmentRequest request) {
        SkillDevelopmentRequest createdRequest = skillRequestService.createRequest(request);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SkillDevelopmentRequest>> getAllSkillRequests() {
        List<SkillDevelopmentRequest> requests = skillRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SkillDevelopmentRequest>> getRequestsByEmployeeId(@PathVariable String employeeId) {
        List<SkillDevelopmentRequest> requests = skillRequestService.getRequestsByEmployeeId(employeeId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SkillDevelopmentRequest>> getRequestsByStatus(@PathVariable String status) {
        List<SkillDevelopmentRequest> requests = skillRequestService.getRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDevelopmentRequest> getRequestById(@PathVariable Long id) {
        Optional<SkillDevelopmentRequest> request = skillRequestService.getRequestById(id);
        if (request.isPresent()) {
            return ResponseEntity.ok(request.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SkillDevelopmentRequest> updateRequestStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            String hrComments = request.get("hrComments");
            SkillDevelopmentRequest updatedRequest = skillRequestService.updateRequestStatus(id, status, hrComments);
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
