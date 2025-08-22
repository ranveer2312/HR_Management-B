package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.AuthRequest;
import com.example.storemanagementbackend.dto.AuthResponse;
import com.example.storemanagementbackend.dto.RegisterRequest;
import com.example.storemanagementbackend.model.Employee;
import com.example.storemanagementbackend.repository.EmployeeRepository;
import com.example.storemanagementbackend.repository.UserRepository;
import com.example.storemanagementbackend.service.AuthService;
import com.example.storemanagementbackend.service.JwtService;
import com.example.storemanagementbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.Map;

import com.example.storemanagementbackend.entity.User;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "https://hr-management-f.vercel.app", "http://idmstiranga.online", "http://localhost:3000" })
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> unifiedLogin(@RequestBody AuthRequest request) {
        // 1. Try User table (admin, HR, etc.)
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user);
            AuthResponse response = new AuthResponse();
            response.setEmail(user.getEmail());
            response.setRoles(user.getRoles().stream().map(r -> r.getName()).toList());
            response.setToken(token);
            return ResponseEntity.ok(response);
        }
        // 2. Try Employee table
        Employee employee = employeeRepository.findByEmail(request.getEmail()).orElse(null);
        if (employee != null && passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            String token = jwtService.generateToken(employee); // Use the same method for Employee
            AuthResponse response = new AuthResponse();
            response.setEmployeeId(employee.getEmployeeId());
            response.setEmployeeName(employee.getEmployeeName());
            response.setEmail(employee.getEmail());
            response.setDepartment(employee.getDepartment());
            response.setPosition(employee.getPosition());
            response.setStatus(employee.getStatus());
            response.setRoles(List.of("EMPLOYEE"));
            response.setToken(token);
            return ResponseEntity.ok(response);
        }
        // 3. If neither, return error
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        // Generate a 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        // Send OTP email
        emailService.sendOtpEmail(email, otp);
        // In a real app, save OTP to DB or cache for later verification
        return ResponseEntity.ok("Password reset instructions sent to your email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        String newPassword = body.get("newPassword");
        // Simulate OTP validation (in real app, check against DB or cache)
        if (otp == null || otp.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or missing OTP");
        }
        // Try to find user by email
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok("Password reset successful for user.");
        }
        // Try to find employee by email
        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            employee.setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(employee);
            return ResponseEntity.ok("Password reset successful for employee.");
        }
        return ResponseEntity.status(404).body("Email not found");
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("No token provided");
            }

            String token = authHeader.substring(7);
            String email = jwtService.extractUsername(token);

            if (email == null) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            // Try User table first
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                AuthResponse response = new AuthResponse();
                response.setEmail(user.getEmail());
                response.setRoles(user.getRoles().stream().map(r -> r.getName()).toList());
                return ResponseEntity.ok(response);
            }

            // Try Employee table
            Employee employee = employeeRepository.findByEmail(email).orElse(null);
            if (employee != null) {
                AuthResponse response = new AuthResponse();
                response.setEmployeeId(employee.getEmployeeId());
                response.setEmployeeName(employee.getEmployeeName());
                response.setEmail(employee.getEmail());
                response.setDepartment(employee.getDepartment());
                response.setPosition(employee.getPosition());
                response.setStatus(employee.getStatus());
                response.setRoles(List.of("EMPLOYEE"));
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(404).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving user information");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(java.util.Map.of(
                "message", "Auth service is working",
                "timestamp", java.time.LocalDateTime.now().toString()));
    }

    @PostMapping("/get-employee-id")
    public ResponseEntity<?> getEmployeeId(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            return ResponseEntity.ok(java.util.Map.of(
                    "employeeId", employee.getEmployeeId(),
                    "employeeName", employee.getEmployeeName()));
        }

        return ResponseEntity.status(404).body("Employee not found");
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId).orElse(null);
        if (employee != null) {
            return ResponseEntity.ok(java.util.Map.of(
                    "employeeId", employee.getEmployeeId(),
                    "employeeName", employee.getEmployeeName(),
                    "email", employee.getEmail(),
                    "department", employee.getDepartment(),
                    "position", employee.getPosition()));
        }
        return ResponseEntity.status(404).body("Employee not found");
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeId, @RequestBody Map<String, String> updates) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId).orElse(null);
        if (employee == null) {
            return ResponseEntity.status(404).body("Employee not found");
        }

        if (updates.containsKey("employeeName"))
            employee.setEmployeeName(updates.get("employeeName"));
        if (updates.containsKey("department"))
            employee.setDepartment(updates.get("department"));
        if (updates.containsKey("position"))
            employee.setPosition(updates.get("position"));
        if (updates.containsKey("email"))
            employee.setEmail(updates.get("email"));

        employeeRepository.save(employee);

        return ResponseEntity.ok(java.util.Map.of(
                "employeeId", employee.getEmployeeId(),
                "employeeName", employee.getEmployeeName(),
                "email", employee.getEmail(),
                "department", employee.getDepartment(),
                "position", employee.getPosition()));
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        java.util.List<Employee> employees = employeeRepository.findAll();
        java.util.List<java.util.Map<String, String>> employeeList = employees.stream()
                .map(emp -> java.util.Map.of(
                        "employeeId", emp.getEmployeeId(),
                        "employeeName", emp.getEmployeeName(),
                        "email", emp.getEmail(),
                        "department", emp.getDepartment(),
                        "position", emp.getPosition()))
                .toList();
        return ResponseEntity.ok(employeeList);
    }
}
