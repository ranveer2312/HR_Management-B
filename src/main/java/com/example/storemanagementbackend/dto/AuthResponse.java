package com.example.storemanagementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private List<String> roles;
    private String token;
    private String employeeId;
    private String employeeName;
    private String department;
    private String position;
    private String status;
} 