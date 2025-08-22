package com.example.storemanagementbackend.repository;

import com.example.storemanagementbackend.model.PettyCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PettyCashRepository extends JpaRepository<PettyCash, Long> {
}