package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.model.Attendance;
import com.example.storemanagementbackend.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    /**
     * Marks an employee's attendance (sign-in or sign-out).
     */
    public Attendance markAttendance(Attendance attendanceRequest) {
        LocalDate today = LocalDate.now();
        if (attendanceRequest.getDate() == null) {
            attendanceRequest.setDate(today);
        }

        Optional<Attendance> existingAttendanceOptional = attendanceRepository
                .findByEmployeeIdAndDate(attendanceRequest.getEmployeeId(), attendanceRequest.getDate());

        Attendance recordToSave;

        if (existingAttendanceOptional.isPresent()) {
            recordToSave = existingAttendanceOptional.get();

            // ✅ Update work location if provided
            if (attendanceRequest.getWorkLocation() != null) {
                recordToSave.setWorkLocation(attendanceRequest.getWorkLocation());
            }

            // Handle Sign-In
            if (attendanceRequest.getCheckInTime() != null && recordToSave.getCheckInTime() == null) {
                recordToSave.setCheckInTime(attendanceRequest.getCheckInTime());
                recordToSave.setStatus("present");
                recordToSave.setWorkHours(0.0);
            }

            // Handle Sign-Out
            if (attendanceRequest.getCheckOutTime() != null &&
                    recordToSave.getCheckInTime() != null &&
                    recordToSave.getCheckOutTime() == null) {

                recordToSave.setCheckOutTime(attendanceRequest.getCheckOutTime());

                long minutesWorked = ChronoUnit.MINUTES.between(
                        recordToSave.getCheckInTime(),
                        recordToSave.getCheckOutTime());
                double hoursWorked = minutesWorked / 60.0;

                if (hoursWorked < 4.5) {
                    recordToSave.setStatus("absent");
                } else if (hoursWorked < 9) {
                    recordToSave.setStatus("half-day");
                } else {
                    recordToSave.setStatus("present");
                }

                recordToSave.setWorkHours(Math.round(hoursWorked * 10.0) / 10.0);
            } else if (attendanceRequest.getCheckOutTime() != null &&
                    recordToSave.getCheckInTime() == null) {
                throw new IllegalArgumentException("Cannot sign out without a prior sign-in for the day.");
            }

        } else {
            // New record
            recordToSave = new Attendance();
            recordToSave.setEmployeeId(attendanceRequest.getEmployeeId());
            recordToSave.setDate(attendanceRequest.getDate());
            recordToSave.setWorkHours(0.0);

            // ✅ Set work location from request
            recordToSave.setWorkLocation(attendanceRequest.getWorkLocation());

            if (attendanceRequest.getCheckInTime() != null) {
                recordToSave.setCheckInTime(attendanceRequest.getCheckInTime());
                recordToSave.setStatus("present");
            } else if (attendanceRequest.getCheckOutTime() != null) {
                throw new IllegalArgumentException("Cannot sign out without a prior sign-in.");
            } else {
                recordToSave.setStatus("absent");
            }
        }

        if (recordToSave.getStatus() == null && recordToSave.getCheckInTime() != null) {
            recordToSave.setStatus("present");
        }

        return attendanceRepository.save(recordToSave);
    }

    public List<Attendance> getAttendanceByEmployee(String employeeId) {
        return attendanceRepository.findByEmployeeIdOrderByDateDesc(employeeId);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}
