package com.example.storemanagementbackend.controller;
 
import com.example.storemanagementbackend.dto.RentDTO;
import com.example.storemanagementbackend.service.RentService;
import com.example.storemanagementbackend.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
 
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
 
@RestController
@RequestMapping("/api/rent")
public class RentController {
    private final RentService rentService;
 
    @Autowired
    private FileStorageService fileStorageService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }
 
    @PostMapping
    public ResponseEntity<?> createRent(@RequestBody RentDTO rentDTO) {
        try {
            return ResponseEntity.ok(rentService.createRent(rentDTO));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invoice number already exists"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
 
    @PostMapping("/upload")
    public ResponseEntity<?> createRentWithFile(@RequestParam("file") MultipartFile file,
            @RequestParam("rentData") String rentDataJson) {
        try {
            RentDTO rentDTO = parseRentData(rentDataJson);
            processFileUpload(file, rentDTO);
            return ResponseEntity.ok(rentService.createRent(rentDTO));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invoice number already exists"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
 
    @PostMapping("/upload/{id}")
    public ResponseEntity<?> updateRentWithFile(@PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("rentData") String rentDataJson) {
        try {
            RentDTO rentDTO = parseRentData(rentDataJson);
            processFileUpload(file, rentDTO);
            return ResponseEntity.ok(rentService.updateRent(id, rentDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
 
    @GetMapping
    public ResponseEntity<List<RentDTO>> getAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRent(@PathVariable Long id, @RequestBody RentDTO rentDTO) {
        try {
            return ResponseEntity.ok(rentService.updateRent(id, rentDTO));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invoice number already exists"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
        return ResponseEntity.ok().build();
    }
 
    private RentDTO parseRentData(String rentDataJson) throws Exception {
        return objectMapper.readValue(rentDataJson, RentDTO.class);
    }
 
    private void processFileUpload(MultipartFile file, RentDTO rentDTO) {
        String fileName = fileStorageService.storeFile(file);
        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
        rentDTO.setDocumentPath(downloadUri);
    }
 
}
 
 
