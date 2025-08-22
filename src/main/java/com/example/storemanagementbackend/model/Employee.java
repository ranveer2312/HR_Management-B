package com.example.storemanagementbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private String employeeName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;

    private String bloodGroup;

    private String profilePhotoUrl; // Cloudinary secure URL

    private String profilePhotoPublicId; // Cloudinary public ID for deletion/updating

    @Column(columnDefinition = "TEXT")
    private String currentAddress;

    @Column(columnDefinition = "TEXT")
    private String permanentAddress;

    private String password;

    private String position;

    private String department;

    private LocalDate joiningDate;

    private LocalDate relievingDate;

    private String status; // Joining, Active, Relieving

    // private String breakStatus; // AVAILABLE, ON_BREAK
    //
}
