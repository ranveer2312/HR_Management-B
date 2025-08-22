package com.example.storemanagementbackend.controller;


import com.example.storemanagementbackend.dto.CommissionsDTO;
import com.example.storemanagementbackend.service.CommissionsService;
import com.example.storemanagementbackend.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;


@RestController
@RequestMapping("/api/commissions")
public class CommissionsController {
    private final CommissionsService commissionsService;


    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ObjectMapper objectMapper;


    public CommissionsController(CommissionsService commissionsService) {
        this.commissionsService = commissionsService;
    }


    @PostMapping
    public ResponseEntity<CommissionsDTO> createCommission(@RequestBody CommissionsDTO commissionsDTO) {
        return ResponseEntity.ok(commissionsService.createCommission(commissionsDTO));
    }


    @PostMapping("/upload")
    public ResponseEntity<CommissionsDTO> createCommissionWithFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("commissionData") String commissionDataJson
    ) {
        try {
            CommissionsDTO commissionsDTO = objectMapper.readValue(commissionDataJson, CommissionsDTO.class);
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
            commissionsDTO.setDocumentPath(downloadUri);
            return ResponseEntity.ok(commissionsService.createCommission(commissionsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<CommissionsDTO>> getAllCommissions() {
        return ResponseEntity.ok(commissionsService.getAllCommissions());
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommissionsDTO> updateCommission(@PathVariable Long id, @RequestBody CommissionsDTO commissionsDTO) {
        return ResponseEntity.ok(commissionsService.updateCommission(id, commissionsDTO));
    }


    @PostMapping("/upload/{id}")
    public ResponseEntity<CommissionsDTO> updateCommissionWithFile(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file,
        @RequestParam("commissionData") String commissionDataJson
    ) {
        try {
            CommissionsDTO commissionsDTO = objectMapper.readValue(commissionDataJson, CommissionsDTO.class);
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
            commissionsDTO.setDocumentPath(downloadUri);
            return ResponseEntity.ok(commissionsService.updateCommission(id, commissionsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommission(@PathVariable Long id) {
        commissionsService.deleteCommission(id);
        return ResponseEntity.ok().build();
    }
}

