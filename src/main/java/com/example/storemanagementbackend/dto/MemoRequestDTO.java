package com.example.storemanagementbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoRequestDTO {
    private String title;
    private String meetingType;
    private java.time.LocalDate meetingDate;
    private String priority;
    private String content;
    private String sentBy; // Admin's employee ID
    private String sentByName; // Admin's name
    private List<String> recipientEmployeeIds; // Specific employee IDs to send to
    private List<String> recipientDepartments; // Specific departments to send to
    private boolean sentToAll; // If true, send to all employees

    public String getTitle() { return title; }
    public String getMeetingType() { return meetingType; }
    public java.time.LocalDate getMeetingDate() { return meetingDate; }
    public String getPriority() { return priority; }
    public String getContent() { return content; }
} 