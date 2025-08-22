package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.dto.GstCalculationRequest;
import com.example.storemanagementbackend.dto.GstCalculationResponse;

public interface GstService {
    GstCalculationResponse calculateGst(GstCalculationRequest request);
}

