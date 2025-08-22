package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.TdsEntryRequest;
import com.example.storemanagementbackend.dto.TdsEntryResponse;
import com.example.storemanagementbackend.service.TdsEntryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tds")
public class TdsEntryController {

    private final TdsEntryService tdsEntryService;

    public TdsEntryController(TdsEntryService tdsEntryService) {
        this.tdsEntryService = tdsEntryService;
    }

    @PostMapping
    public ResponseEntity<TdsEntryResponse> createTdsEntry(@RequestBody TdsEntryRequest request) {
        TdsEntryResponse response = tdsEntryService.createTdsEntry(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TdsEntryResponse>> getAllTdsEntries() {
        List<TdsEntryResponse> entries = tdsEntryService.getAllTdsEntries();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TdsEntryResponse> getTdsEntryById(@PathVariable Long id) {
        Optional<TdsEntryResponse> entry = tdsEntryService.getTdsEntryById(id);
        return entry.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TdsEntryResponse> updateTdsEntry(@PathVariable Long id, @RequestBody TdsEntryRequest request) {
        TdsEntryResponse response = tdsEntryService.updateTdsEntry(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTdsEntry(@PathVariable Long id) {
        tdsEntryService.deleteTdsEntry(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<TdsEntryResponse>> getTdsEntriesByCategory(@RequestParam String category) {
        List<TdsEntryResponse> entries = tdsEntryService.getTdsEntriesByCategory(category);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/search/date-range")
    public ResponseEntity<List<TdsEntryResponse>> getTdsEntriesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TdsEntryResponse> entries = tdsEntryService.getTdsEntriesByDateRange(startDate, endDate);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/search/category-and-date")
    public ResponseEntity<List<TdsEntryResponse>> getTdsEntriesByCategoryAndDateRange(
            @RequestParam String category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TdsEntryResponse> entries = tdsEntryService.getTdsEntriesByCategoryAndDateRange(category, startDate, endDate);
        return ResponseEntity.ok(entries);
    }
}

