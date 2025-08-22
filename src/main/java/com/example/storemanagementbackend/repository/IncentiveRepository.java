package com.example.storemanagementbackend.repository;


import com.example.storemanagementbackend.model.Incentive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IncentiveRepository extends JpaRepository<Incentive, Long> {
}

