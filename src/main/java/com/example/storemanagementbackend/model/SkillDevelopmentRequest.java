package com.example.storemanagementbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "skill_development_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDevelopmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false)
    private String skillName;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(nullable = false)
    private String priority; // HIGH, MEDIUM, LOW

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED

    @Column(nullable = false)
    private LocalDateTime requestDate;

    private LocalDateTime responseDate;

    private String hrComments;
}