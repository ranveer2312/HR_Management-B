package com.example.storemanagementbackend.repository;

import com.example.storemanagementbackend.model.GstCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GstCalculationRepository extends JpaRepository<GstCalculation, Long> {
    // Optionally add queries if you want filtering later
}

