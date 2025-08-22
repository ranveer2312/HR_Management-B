package com.example.storemanagementbackend.repository;
 
import com.example.storemanagementbackend.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {
}