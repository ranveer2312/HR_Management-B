package com.example.storemanagementbackend.service.impl;
 
import com.example.storemanagementbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
 
@Service
public class EmailServiceImpl implements EmailService {
 
    private final JavaMailSender mailSender;
 
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
 
//    @Override
//    public void sendResetEmail(String toEmail, String resetLink) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Reset Your Password");
//        message.setText("Click the link below to reset your password:\n" + resetLink);
//        message.setFrom("your-email@example.com");  // Replace with your sending email
//
//        mailSender.send(message);
//    }
@Override
public void sendOtpEmail(String toEmail, String otp) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject("Your Password Reset OTP");
    message.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.");
    message.setFrom("your-email@example.com");
 
    mailSender.send(message);
}

    private void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("your-email@example.com");
 
        mailSender.send(message);
    }
 
    @Override
    public void sendBreakViolationEmail(String employeeId, String employeeName, String breakType, int durationMinutes, int maxAllowedMinutes) {
        String subject = "Break Time Violation Alert - " + employeeName;
        String body = String.format(
            "Dear HR Team,\n\n" +
            "An employee has exceeded the allowed break time:\n\n" +
            "Employee ID: %s\n" +
            "Employee Name: %s\n" +
            "Break Type: %s\n" +
            "Duration Taken: %d minutes\n" +
            "Maximum Allowed: %d minutes\n" +
            "Violation: %d minutes over limit\n\n" +
            "Please review this employee's break time management.\n\n" +
            "Best regards,\n" +
            "Attendance System",
            employeeId, employeeName, breakType, durationMinutes, maxAllowedMinutes, 
            durationMinutes - maxAllowedMinutes
        );
        
        sendEmail("swathin8088@gmail.com", subject, body);
    }

    @Override
    public void sendBreakWindowViolationEmail(String employeeId, String employeeName, String breakType, int durationMinutes, int minutesOverWindow, String startTime, String endTime) {
        String subject = "Break Window Violation Alert - " + employeeName;
        String body = String.format(
            "Dear HR Team,\n\n" +
            "An employee has extended break beyond allowed time window:\n\n" +
            "Employee ID: %s\n" +
            "Employee Name: %s\n" +
            "Break Type: %s\n" +
            "Start Time: %s\n" +
            "End Time: %s\n" +
            "Duration Taken: %d minutes\n" +
            "Extended beyond window by: %d minutes\n\n" +
            "Please review this employee's break time management.\n\n" +
            "Best regards,\n" +
            "Attendance System",
            employeeId, employeeName, breakType, startTime, endTime, durationMinutes, minutesOverWindow
        );
        
        sendEmail("swathin8088@gmail.com", subject, body);
    }
 
}
//package com.example.register_page.service.impl;
//
//import com.example.register_page.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailServiceImpl implements EmailService {
//
//    private final JavaMailSender mailSender;
//
//    @Autowired
//    public EmailServiceImpl(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    @Override
//    public void sendResetEmail(String toEmail, String resetLink) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Reset Your Password");
//        message.setText("Click the link below to reset your password:\n" + resetLink);
//        message.setFrom("your-email@example.com"); // Replace with a valid sender address
//
//        mailSender.send(message);
//    }
//}
 
 