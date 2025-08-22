package com.example.storemanagementbackend.repository;

import com.example.storemanagementbackend.model.TdsEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TdsEntryRepository extends JpaRepository<TdsEntry, Long> {
    List<TdsEntry> findByTdsForIgnoreCase(String tdsFor);
    List<TdsEntry> findByDateBetween(LocalDate start, LocalDate end);
    List<TdsEntry> findByTdsForIgnoreCaseAndDateBetween(String tdsFor, LocalDate start, LocalDate end);
}

