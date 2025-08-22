package com.example.storemanagementbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "training_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long trainingId;

    @Column(nullable = false)
    private String employeeId;
}