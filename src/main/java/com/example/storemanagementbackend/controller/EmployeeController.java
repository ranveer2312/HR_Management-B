package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.model.Employee;
import com.example.storemanagementbackend.service.EmployeeService;
import com.example.storemanagementbackend.dto.EmployeeRegistrationRequest;
import com.example.storemanagementbackend.repository.EmployeeDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = { "http://31.97.205.86:3000", "https://hr-management-f.vercel.app" })
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;
    private final EmployeeDocumentRepository documentRepository;
    private final Cloudinary cloudinary;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
            ObjectMapper objectMapper,
            EmployeeDocumentRepository documentRepository,
            Cloudinary cloudinary) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
        this.documentRepository = documentRepository;
        this.cloudinary = cloudinary;
    }

    /** Create Employee with optional profile photo */
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestParam("employee") String employeeStr,
            @RequestParam(name = "photo", required = false) MultipartFile photo) {
        Employee employee;
        try {
            employee = objectMapper.readValue(employeeStr, Employee.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid employee JSON: " + e.getMessage());
        }

        try {
            if (photo != null && !photo.isEmpty()) {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(
                        photo.getBytes(),
                        ObjectUtils.asMap("folder", "employees/profile_photos"));
                String photoUrl = uploadResult.get("secure_url").toString();
                employee.setProfilePhotoUrl(photoUrl);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Photo upload failed: " + e.getMessage());
        }

        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    /** Get employee by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Get employee by employeeId */
    @GetMapping("/byEmployeeId/{employeeId}")
    public ResponseEntity<Employee> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        try {
            Employee employee = employeeService.getEmployeeByEmployeeId(employeeId);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Get all employees */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /** Update employee */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestParam("employee") String employeeStr,
            @RequestParam(name = "photo", required = false) MultipartFile photo) {
        try {
            Employee employeeDetails = objectMapper.readValue(employeeStr, Employee.class);

            if (photo != null && !photo.isEmpty()) {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(
                        photo.getBytes(),
                        ObjectUtils.asMap("folder", "employees/profile_photos"));
                String photoUrl = uploadResult.get("secure_url").toString();
                employeeDetails.setProfilePhotoUrl(photoUrl);
            }

            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Update failed: " + e.getMessage());
        }
    }

    /** Delete employee */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Login */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        Optional<Employee> employeeOpt = employeeService.getAllEmployees().stream()
                .filter(emp -> email.equals(emp.getEmail()) && password.equals(emp.getPassword()))
                .findFirst();
        return employeeOpt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password"));
    }

    /** Register employee (basic) */
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(
            @RequestBody com.example.storemanagementbackend.dto.RegisterRequest request) {
        Employee employee = new Employee();
        employee.setEmployeeName(request.getFullName() != null ? request.getFullName() : request.getUsername());
        employee.setEmployeeId(request.getUsername());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());
        employee.setPhoneNumber(null);

        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    /** HR register employee */
    @PostMapping("/hr/register")
    public ResponseEntity<Employee> hrRegisterEmployee(@RequestBody EmployeeRegistrationRequest request) {
        Employee employee = new Employee();
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeId(request.getEmployeeId());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());
        employee.setPhoneNumber(request.getPhoneNumber());

        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }
}
