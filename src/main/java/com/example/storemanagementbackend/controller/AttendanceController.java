package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.model.Attendance;
import com.example.storemanagementbackend.repository.AttendanceRepository;
import com.example.storemanagementbackend.dto.AttendanceWithEmployeeDTO;
import com.example.storemanagementbackend.dto.EmployeeAttendanceStatusDTO;
import com.example.storemanagementbackend.model.Employee;
import com.example.storemanagementbackend.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = { "https://hr-management-f.vercel.app", "http://idmstiranga.online",
        "http://localhost:3000" }, allowCredentials = "true", allowedHeaders = "*")
public class AttendanceController {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // ✅ Endpoint to get all attendance records
    @GetMapping
    public ResponseEntity<List<AttendanceWithEmployeeDTO>> getAllAttendance() {
        List<Attendance> attendanceRecords = attendanceRepository.findAll();
        List<AttendanceWithEmployeeDTO> result = attendanceRecords.stream().map(att -> {
            AttendanceWithEmployeeDTO dto = new AttendanceWithEmployeeDTO();
            dto.setId(att.getId());
            dto.setEmployeeId(att.getEmployeeId());
            dto.setDate(att.getDate());
            dto.setCheckInTime(att.getCheckInTime());
            dto.setCheckOutTime(att.getCheckOutTime());
            dto.setStatus(att.getStatus());
            dto.setWorkHours(att.getWorkHours());
            dto.setWorkLocation(att.getWorkLocation()); // ✅ include work location

            // Fetch employee details
            Employee emp = employeeRepository.findByEmployeeId(att.getEmployeeId()).orElse(null);
            if (emp != null) {
                dto.setEmployeeName(emp.getEmployeeName());
                dto.setDepartment(emp.getDepartment());
            }
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // ✅ Endpoint to get attendance for a specific employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendance(@PathVariable String employeeId) {
        List<Attendance> attendanceRecords = attendanceRepository.findByEmployeeIdOrderByDateDesc(employeeId);
        return ResponseEntity.ok(attendanceRecords);
    }

    // ✅ Endpoint to mark attendance (sign-in or sign-out)
    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody java.util.Map<String, Object> request) {
        try {
            String employeeId = (String) request.get("employeeId");
            String checkInTimeStr = (String) request.get("checkInTime");
            String checkOutTimeStr = (String) request.get("checkOutTime");
            String workLocation = (String) request.get("workLocation"); // ✅ new

            logger.info("Attendance request received for employee: {}", employeeId);

            // Check if employee exists
            Optional<Employee> employeeOpt = employeeRepository.findByEmployeeId(employeeId);
            if (employeeOpt.isEmpty()) {
                logger.warn("Employee with ID {} not found in employees table", employeeId);
                return ResponseEntity.status(404).body("Employee not found with ID: " + employeeId);
            }

            LocalDate today = LocalDate.now();

            // Find existing record for today
            Optional<Attendance> existingAttendanceOpt = attendanceRepository.findByEmployeeIdAndDate(employeeId,
                    today);

            if (existingAttendanceOpt.isPresent()) {
                Attendance existing = existingAttendanceOpt.get();
                if (existing.getCheckInTime() != null && existing.getCheckOutTime() != null) {
                    return ResponseEntity.badRequest()
                            .body("Attendance already completed for today. Cannot modify existing record.");
                }
                if (existing.getCheckInTime() != null && checkInTimeStr != null) {
                    return ResponseEntity.badRequest()
                            .body("Check-in already recorded for today. Only check-out is allowed.");
                }
                if (existing.getCheckOutTime() != null && checkOutTimeStr != null) {
                    return ResponseEntity.badRequest()
                            .body("Check-out already recorded for today. Only check-in is allowed.");
                }
            }

            Attendance recordToSave;
            if (existingAttendanceOpt.isPresent()) {
                recordToSave = existingAttendanceOpt.get();
            } else {
                recordToSave = new Attendance();
                recordToSave.setEmployeeId(employeeId);
                recordToSave.setDate(today);
                recordToSave.setStatus("absent");
                recordToSave.setWorkHours(0.0);
            }

            // ✅ Save work location if provided
            if (workLocation != null && !workLocation.isEmpty()) {
                recordToSave.setWorkLocation(workLocation);
            }

            // Handle Sign In Logic
            if (checkInTimeStr != null && recordToSave.getCheckInTime() == null) {
                recordToSave.setCheckInTime(LocalTime.parse(checkInTimeStr));
                recordToSave.setStatus("present");
                recordToSave.setWorkHours(0.0);
            }

            // Handle Sign Out Logic
            if (checkOutTimeStr != null && recordToSave.getCheckInTime() != null
                    && recordToSave.getCheckOutTime() == null) {
                recordToSave.setCheckOutTime(LocalTime.parse(checkOutTimeStr));

                if (recordToSave.getCheckInTime() != null && recordToSave.getCheckOutTime() != null) {
                    long minutesWorked = ChronoUnit.MINUTES.between(recordToSave.getCheckInTime(),
                            recordToSave.getCheckOutTime());
                    double hoursWorked = minutesWorked / 60.0;

                    if (hoursWorked < 4.5) {
                        recordToSave.setStatus("absent");
                    } else if (hoursWorked < 9) {
                        recordToSave.setStatus("half-day");
                    } else {
                        recordToSave.setStatus("present");
                    }
                    recordToSave.setWorkHours(Double.valueOf(String.format("%.1f", hoursWorked)));
                }
            }

            if (recordToSave.getStatus() == null && recordToSave.getCheckInTime() != null) {
                recordToSave.setStatus("present");
            }

            Attendance savedAttendance = attendanceRepository.save(recordToSave);
            logger.info("Attendance saved successfully for employee: {}", savedAttendance.getEmployeeId());

            return ResponseEntity.ok(savedAttendance);

        } catch (Exception e) {
            logger.error("Error marking attendance for employee: {}", request.get("employeeId"), e);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    // ✅ Endpoint to get all employees with their attendance status for today
    @GetMapping("/today-status")
    public ResponseEntity<List<EmployeeAttendanceStatusDTO>> getTodayAttendanceStatus() {
        LocalDate today = LocalDate.now();
        List<Employee> allEmployees = employeeRepository.findAll();

        List<EmployeeAttendanceStatusDTO> result = allEmployees.stream().map(emp -> {
            EmployeeAttendanceStatusDTO dto = new EmployeeAttendanceStatusDTO();
            dto.setEmployeeId(emp.getEmployeeId());
            dto.setEmployeeName(emp.getEmployeeName());
            dto.setDepartment(emp.getDepartment());
            dto.setEmail(emp.getEmail());
            dto.setDate(today);

            Optional<Attendance> todayAttendance = attendanceRepository.findByEmployeeIdAndDate(emp.getEmployeeId(),
                    today);

            if (todayAttendance.isPresent()) {
                Attendance attendance = todayAttendance.get();
                dto.setCheckInTime(attendance.getCheckInTime());
                dto.setCheckOutTime(attendance.getCheckOutTime());
                dto.setStatus(attendance.getStatus());
                dto.setWorkHours(attendance.getWorkHours());
                dto.setWorkLocation(attendance.getWorkLocation()); // ✅ added

                if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
                    dto.setAttendanceStatus("completed");
                } else if (attendance.getCheckInTime() != null) {
                    dto.setAttendanceStatus("checkin_only");
                } else {
                    dto.setAttendanceStatus("not_marked");
                }
            } else {
                dto.setCheckInTime(null);
                dto.setCheckOutTime(null);
                dto.setStatus("not_marked");
                dto.setWorkHours(0.0);
                dto.setAttendanceStatus("not_marked");
                dto.setWorkLocation(null);
            }

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
