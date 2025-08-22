package com.example.storemanagementbackend.service.impl;

import com.example.storemanagementbackend.service.TdsEntryService;
import com.example.storemanagementbackend.dto.TdsCalculationRequest;
import com.example.storemanagementbackend.dto.TdsCalculationResponse;
import com.example.storemanagementbackend.dto.TdsEntryRequest;
import com.example.storemanagementbackend.dto.TdsEntryResponse;
import com.example.storemanagementbackend.model.TdsEntry;
import com.example.storemanagementbackend.repository.TdsEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.time.LocalDate;

@Service
public class TdsEntryServiceImpl implements TdsEntryService {

    private final TdsEntryRepository repository;

    public TdsEntryServiceImpl(TdsEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public TdsEntryResponse createTdsEntry(TdsEntryRequest request) {
        TdsEntry entry = new TdsEntry();
        copyFromRequest(request, entry);
        calculateAmounts(entry);
        return toResponse(repository.save(entry));
    }

    @Override
    public List<TdsEntryResponse> getAllTdsEntries() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<TdsEntryResponse> getTdsEntryById(Long id) {
        return repository.findById(id).map(this::toResponse);
    }

    @Override
    public TdsEntryResponse updateTdsEntry(Long id, TdsEntryRequest request) {
        Optional<TdsEntry> optionalEntry = repository.findById(id);
        if (optionalEntry.isEmpty()) {
            throw new RuntimeException("TDS Entry not found");
        }
        TdsEntry entry = optionalEntry.get();
        copyFromRequest(request, entry);
        calculateAmounts(entry);
        return toResponse(repository.save(entry));
    }

    @Override
    public void deleteTdsEntry(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TdsEntryResponse> getTdsEntriesByCategory(String category) {
        return repository.findByTdsForIgnoreCase(category).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<TdsEntryResponse> getTdsEntriesByDateRange(LocalDate startDate, LocalDate endDate) {
        return repository.findByDateBetween(startDate, endDate).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<TdsEntryResponse> getTdsEntriesByCategoryAndDateRange(String category, LocalDate startDate, LocalDate endDate) {
        return repository.findByTdsForIgnoreCaseAndDateBetween(category, startDate, endDate).stream().map(this::toResponse).collect(Collectors.toList());
    }
    @Override
public TdsCalculationResponse calculateTds(TdsCalculationRequest request) {
    double gstAmount = (request.getTaxableAmount() * request.getGstRate()) / 100;
    double tdsAmount = (request.getTaxableAmount() * request.getTdsRate()) / 100;
    double total = request.getTaxableAmount() + gstAmount - tdsAmount;

    TdsCalculationResponse response = new TdsCalculationResponse();
    response.setGstAmount(gstAmount);
    response.setTdsAmount(tdsAmount);
    response.setTotalPayment(total);

    return response;
}

@Override
public Double getTotalTdsAmountByDateRange(LocalDate startDate, LocalDate endDate) {
    return repository.findByDateBetween(startDate, endDate)
            .stream()
            .mapToDouble(entry -> entry.getTdsAmount())
            .sum();
}

@Override
public Double getTotalTaxableAmountByDateRange(LocalDate startDate, LocalDate endDate) {
    return repository.findByDateBetween(startDate, endDate)
            .stream()
            .mapToDouble(entry -> entry.getTaxableAmount())
            .sum();
}


    private void calculateAmounts(TdsEntry entry) {
        // Amounts are already calculated in frontend, just copy them
        // No additional calculation needed
    }

    private void copyFromRequest(TdsEntryRequest request, TdsEntry entry) {
        entry.setTdsFor(request.getTdsFor());
        entry.setTdsRate(request.getTdsRate());
        entry.setTaxableAmount(request.getTaxableAmount());
        entry.setAmount(request.getTaxableAmount());
        entry.setGstRate(request.getGstRate());
        entry.setGstAmount(request.getGstAmount());
        entry.setTdsAmount(request.getTdsAmount());
        entry.setTotalPayment(request.getTotalPayment());
        entry.setRemarks(request.getRemarks());
        entry.setDate(LocalDate.parse(request.getDate()));
    }

    private TdsEntryResponse toResponse(TdsEntry entry) {
        TdsEntryResponse res = new TdsEntryResponse();
        res.setId(entry.getId());
        res.setTdsFor(entry.getTdsFor());
        res.setTdsRate(entry.getTdsRate());
        res.setTaxableAmount(entry.getTaxableAmount());
        res.setGstRate(entry.getGstRate());
        res.setGstAmount(entry.getGstAmount());
        res.setTdsAmount(entry.getTdsAmount());
        res.setTotalPayment(entry.getTotalPayment());
        res.setRemarks(entry.getRemarks());
        res.setDate(entry.getDate().toString());
        return res;
    }
}

