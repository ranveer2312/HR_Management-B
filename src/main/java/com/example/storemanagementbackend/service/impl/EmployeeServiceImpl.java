package com.example.storemanagementbackend.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.storemanagementbackend.model.Employee;
import com.example.storemanagementbackend.repository.EmployeeRepository;
import com.example.storemanagementbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Cloudinary cloudinary;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, Cloudinary cloudinary) {
        this.employeeRepository = employeeRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        if (employee.getJoiningDate() == null) {
            employee.setJoiningDate(LocalDate.now());
        }
        if (employee.getStatus() == null || employee.getStatus().isEmpty()) {
            employee.setStatus("Active");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));
    }

    @Override
    public Employee getEmployeeByEmployeeId(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with employee ID: " + employeeId));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        existingEmployee.setEmployeeName(employeeDetails.getEmployeeName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setPhoneNumber(employeeDetails.getPhoneNumber());
        existingEmployee.setBloodGroup(employeeDetails.getBloodGroup());
        existingEmployee.setProfilePhotoUrl(employeeDetails.getProfilePhotoUrl());
        existingEmployee.setProfilePhotoPublicId(employeeDetails.getProfilePhotoPublicId());
        existingEmployee.setCurrentAddress(employeeDetails.getCurrentAddress());
        existingEmployee.setPermanentAddress(employeeDetails.getPermanentAddress());

        if (employeeDetails.getPassword() != null && !employeeDetails.getPassword().isEmpty()) {
            existingEmployee.setPassword(employeeDetails.getPassword()); // ⚠️ Hash this in production
        }

        existingEmployee.setPosition(employeeDetails.getPosition());
        existingEmployee.setDepartment(employeeDetails.getDepartment());
        existingEmployee.setJoiningDate(employeeDetails.getJoiningDate());
        existingEmployee.setRelievingDate(employeeDetails.getRelievingDate());
        existingEmployee.setStatus(employeeDetails.getStatus());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        // delete photo from Cloudinary if exists
        deleteEmployeeProfilePhoto(employee);

        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Employee updateEmployeeStatus(Long id, String status) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));
        employee.setStatus(status);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeProfilePhoto(Employee employee) {
        try {
            if (employee.getProfilePhotoPublicId() != null) {
                cloudinary.uploader().destroy(employee.getProfilePhotoPublicId(), ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            System.err.println("Failed to delete profile photo from Cloudinary: " + e.getMessage());
        }
    }
}
