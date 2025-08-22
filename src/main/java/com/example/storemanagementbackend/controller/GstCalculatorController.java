package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.GstCalculationRequest;
import com.example.storemanagementbackend.dto.GstCalculationResponse;
import com.example.storemanagementbackend.service.GstService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gst")
@CrossOrigin(origins = {"https://idmstiranga.online", "http://idmstiranga.online", "http://localhost:3000"})
public class GstCalculatorController {

    private final GstService gstService;

    public GstCalculatorController(GstService gstService) {
        this.gstService = gstService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<GstCalculationResponse> calculateGst( @RequestBody GstCalculationRequest request) {
        GstCalculationResponse response = gstService.calculateGst(request);
        return ResponseEntity.ok(response);
    }
}

