package com.example.storemanagementbackend.controller;


import com.example.storemanagementbackend.dto.IncentiveDTO;
import com.example.storemanagementbackend.service.IncentiveService;
import com.example.storemanagementbackend.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;
import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/api/incentives/incentive")
public class IncentiveController {
    private final IncentiveService incentiveService;
   
    @Autowired
    private FileStorageService fileStorageService;
   
    @Autowired
    private ObjectMapper objectMapper;


    public IncentiveController(IncentiveService incentiveService) {
        this.incentiveService = incentiveService;
    }


    @PostMapping
    public ResponseEntity<IncentiveDTO> createIncentive(@RequestBody IncentiveDTO incentiveDTO) {
        return ResponseEntity.ok(incentiveService.createIncentive(incentiveDTO));
    }


    @PostMapping("/upload")
    public ResponseEntity<IncentiveDTO> createIncentiveWithFile(@RequestParam("file") MultipartFile file,
                                                               @RequestParam("incentiveData") String incentiveDataJson) {
        try {
            IncentiveDTO incentiveDTO = objectMapper.readValue(incentiveDataJson, IncentiveDTO.class);
           
            // Save file and get download URI
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
           
            // Set document path in the DTO
            incentiveDTO.setDocumentPath(downloadUri);
           
            return ResponseEntity.ok(incentiveService.createIncentive(incentiveDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/upload/{id}")
    public ResponseEntity<IncentiveDTO> updateIncentiveWithFile(@PathVariable Long id,
                                                               @RequestParam("file") MultipartFile file,
                                                               @RequestParam("incentiveData") String incentiveDataJson) {
        try {
            IncentiveDTO incentiveDTO = objectMapper.readValue(incentiveDataJson, IncentiveDTO.class);
           
            // Save file and get download URI
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
           
            // Set document path in the DTO
            incentiveDTO.setDocumentPath(downloadUri);
           
            return ResponseEntity.ok(incentiveService.updateIncentive(id, incentiveDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public Map<String, List<IncentiveDTO>> getAllIncentives() {
        List<IncentiveDTO> incentives = incentiveService.getAllIncentives();
        return Collections.singletonMap("items", incentives);
    }


    @PutMapping("/{id}")
    public ResponseEntity<IncentiveDTO> updateIncentive(@PathVariable Long id, @RequestBody IncentiveDTO incentiveDTO) {
        return ResponseEntity.ok(incentiveService.updateIncentive(id, incentiveDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncentive(@PathVariable Long id) {
        incentiveService.deleteIncentive(id);
        return ResponseEntity.ok().build();
    }
}

