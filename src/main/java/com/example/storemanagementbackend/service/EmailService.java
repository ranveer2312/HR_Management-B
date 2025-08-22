package com.example.storemanagementbackend.service;
 
public interface EmailService {
        //void sendResetEmail(String toEmail, String resetLink);
        void sendOtpEmail(String toEmail, String otp);
        void sendBreakViolationEmail(String employeeId, String employeeName, String breakType, int durationMinutes, int maxAllowedMinutes);
        void sendBreakWindowViolationEmail(String employeeId, String employeeName, String breakType, int durationMinutes, int minutesOverWindow, String startTime, String endTime);
 
}
 
 