package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.ElectricBillsDTO;
import com.example.storemanagementbackend.model.ElectricBills;
import com.example.storemanagementbackend.repository.ElectricBillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/electric-bills")
@CrossOrigin(origins = "https://idmstiranga.online", allowCredentials = "true")
public class ElectricBillsController {

    @Autowired
    private ElectricBillsRepository repository;

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    // ✅ Get all bills
    @GetMapping
    public List<ElectricBillsDTO> getAllBills() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Electric Bills API is working!");
    }

    // ✅ Create new bill
    @PostMapping
    public ResponseEntity<?> createBill(
            @RequestParam String accountNo,
            @RequestParam String paymentDate,
            @RequestParam String paymentMode,
            @RequestParam String billType,
            @RequestParam String month,
            @RequestParam String payment,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(required = false) String date) {

        try {
            ElectricBills bill = new ElectricBills();
            bill.setAccountNo(accountNo);

            // Parse payment date
            bill.setPaymentDate(LocalDate.parse(paymentDate));

            // Handle "date" field (optional)
            if (date != null && !date.isEmpty()) {
                bill.setDate(LocalDate.parse(date));
            } else {
                bill.setDate(LocalDate.now());
            }

            bill.setPaymentMode(paymentMode);
            bill.setBillType(billType);
            bill.setMonth(month);

            BigDecimal paymentValue = new BigDecimal(payment);
            bill.setAmount(paymentValue);
            bill.setPayment(paymentValue);
            bill.setRemarks(remarks);

            // File handling
            if (file != null && !file.isEmpty()) {
                String fileName = saveFile(file);
                bill.setDocumentPath("/uploads/" + fileName);
            }

            ElectricBills saved = repository.save(bill);
            return ResponseEntity.ok(convertToDTO(saved));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // ✅ Update existing bill
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBill(
            @PathVariable Long id,
            @RequestParam String accountNo,
            @RequestParam String paymentDate,
            @RequestParam String paymentMode,
            @RequestParam String billType,
            @RequestParam String month,
            @RequestParam String payment,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(required = false) String date) {

        try {
            return repository.findById(id)
                    .map(bill -> {
                        bill.setAccountNo(accountNo);

                        // Parse payment date safely
                        try {
                            bill.setPaymentDate(LocalDate.parse(paymentDate));
                        } catch (Exception ex) {
                            String[] parts = paymentDate.split(",");
                            if (parts.length == 3) {
                                bill.setPaymentDate(LocalDate.of(
                                        Integer.parseInt(parts[0].trim()),
                                        Integer.parseInt(parts[1].trim()),
                                        Integer.parseInt(parts[2].trim())));
                            } else {
                                throw new RuntimeException("Invalid date format: " + paymentDate);
                            }
                        }

                        bill.setPaymentMode(paymentMode);
                        bill.setBillType(billType);
                        bill.setMonth(month);

                        // Handle "date" field
                        if (date != null && !date.isEmpty()) {
                            bill.setDate(LocalDate.parse(date));
                        } else if (bill.getDate() == null) {
                            bill.setDate(LocalDate.now());
                        }

                        BigDecimal paymentValue = new BigDecimal(payment);
                        bill.setAmount(paymentValue);
                        bill.setPayment(paymentValue);
                        bill.setRemarks(remarks);

                        // File handling
                        if (file != null && !file.isEmpty()) {
                            String fileName = saveFile(file);
                            bill.setDocumentPath("/uploads/" + fileName);
                        }

                        ElectricBills updated = repository.save(bill);
                        return ResponseEntity.ok(convertToDTO(updated));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // ✅ Delete bill
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ✅ File saving helper
    private String saveFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File targetFile = new File(uploadDir, fileName);
            file.transferTo(targetFile);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }
    }

    // ✅ DTO conversion helper
    private ElectricBillsDTO convertToDTO(ElectricBills bill) {
        ElectricBillsDTO dto = new ElectricBillsDTO();
        dto.setId(bill.getId());
        dto.setAccountNo(bill.getAccountNo());
        dto.setPaymentDate(bill.getPaymentDate());
        dto.setPaymentMode(bill.getPaymentMode());
        dto.setBillType(bill.getBillType());
        dto.setMonth(bill.getMonth());
        dto.setAmount(bill.getAmount());
        dto.setPayment(bill.getPayment());
        dto.setRemarks(bill.getRemarks());
        dto.setDocumentPath(bill.getDocumentPath());
        dto.setDate(bill.getDate());
        return dto;
    }
}
