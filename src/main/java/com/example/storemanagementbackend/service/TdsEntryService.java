package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.dto.TdsEntryRequest;
import com.example.storemanagementbackend.dto.TdsEntryResponse;
import com.example.storemanagementbackend.dto.TdsCalculationRequest;
import com.example.storemanagementbackend.dto.TdsCalculationResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TdsEntryService {

    // Core CRUD
    TdsEntryResponse createTdsEntry(TdsEntryRequest request);
    List<TdsEntryResponse> getAllTdsEntries();
    Optional<TdsEntryResponse> getTdsEntryById(Long id);
    TdsEntryResponse updateTdsEntry(Long id, TdsEntryRequest request);
    void deleteTdsEntry(Long id);

    // Search
    List<TdsEntryResponse> getTdsEntriesByCategory(String category);
    List<TdsEntryResponse> getTdsEntriesByDateRange(LocalDate startDate, LocalDate endDate);
    List<TdsEntryResponse> getTdsEntriesByCategoryAndDateRange(String category, LocalDate startDate, LocalDate endDate);

    // 🔢 TDS Calculation Features
    TdsCalculationResponse calculateTds(TdsCalculationRequest request);
    Double getTotalTdsAmountByDateRange(LocalDate startDate, LocalDate endDate);
    Double getTotalTaxableAmountByDateRange(LocalDate startDate, LocalDate endDate);
}

