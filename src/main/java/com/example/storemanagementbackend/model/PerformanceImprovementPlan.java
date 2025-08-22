package com.example.storemanagementbackend.model;
 
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
 
@Entity
@Table(name = "performance_improvement_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceImprovementPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Employee employee;
 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanStatus planStatus;
 
    @Column(nullable = false)
    private LocalDate startDate;
 
    @Column(nullable = false)
    private LocalDate endDate;
 
    @Column(columnDefinition = "TEXT")
    private String objectives;
 
    @Column(columnDefinition = "TEXT")
    private String actions;
 
    @Column(columnDefinition = "TEXT")
    private String support;
 
    @Column(nullable = false)
    private LocalDate reviewDate;
 
    @Column(nullable = false)
    private String reviewer;
 
    @Column(columnDefinition = "TEXT")
    private String comments;
 
    @Column(columnDefinition = "TEXT")
    private String goalSetting;
}
 