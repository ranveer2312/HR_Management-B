package com.example.storemanagementbackend.controller;


import com.example.storemanagementbackend.dto.InternetBillsDTO;
import com.example.storemanagementbackend.service.InternetBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/api/internet-bills")
@CrossOrigin(origins = {"https://idmstiranga.online", "http://idmstiranga.online", "http://localhost:3000"})
public class InternetBillsController {
    @Autowired
    private InternetBillsService internetBillsService;


    @PostMapping
    public ResponseEntity<?> createInternetBill(@RequestBody InternetBillsDTO internetBillsDTO) {
        try {
            InternetBillsDTO created = internetBillsService.createInternetBill(internetBillsDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of(
                "error", "Failed to create internet bill",
                "message", e.getMessage()
            ));
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllInternetBills() {
        try {
            System.out.println("InternetBillsController: getAllInternetBills called");
            List<InternetBillsDTO> bills = internetBillsService.getAllInternetBills();
            System.out.println("InternetBillsController: Found " + bills.size() + " bills");
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            System.err.println("InternetBillsController: Error getting bills: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(java.util.Map.of(
                "error", "Internal server error",
                "message", e.getMessage(),
                "details", e.getClass().getSimpleName()
            ));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateInternetBill(@PathVariable Long id, @RequestBody InternetBillsDTO internetBillsDTO) {
        try {
            InternetBillsDTO updated = internetBillsService.updateInternetBill(id, internetBillsDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of(
                "error", "Failed to update internet bill",
                "message", e.getMessage()
            ));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInternetBill(@PathVariable Long id) {
        try {
            internetBillsService.deleteInternetBill(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of(
                "error", "Failed to delete internet bill",
                "message", e.getMessage()
            ));
        }
    }



}
