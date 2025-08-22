package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.PettyCashDTO;
import com.example.storemanagementbackend.service.FileStorageService;
import com.example.storemanagementbackend.service.PettyCashService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/petty-cash")
public class PettyCashController {
    private final PettyCashService pettyCashService;

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ObjectMapper objectMapper;

    public PettyCashController(PettyCashService pettyCashService) {
        this.pettyCashService = pettyCashService;
    }

    @PostMapping
    public ResponseEntity<PettyCashDTO> createPettyCash(@RequestBody PettyCashDTO pettyCashDTO) {
        return ResponseEntity.ok(pettyCashService.createPettyCash(pettyCashDTO));
    }

    @PostMapping("/upload")
    public ResponseEntity<PettyCashDTO> createPettyCashWithFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("pettyCashData") String pettyCashDataJson
    ) {
        try {
            PettyCashDTO pettyCashDTO = objectMapper.readValue(pettyCashDataJson, PettyCashDTO.class);
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
            pettyCashDTO.setDocumentPath(downloadUri);
            return ResponseEntity.ok(pettyCashService.createPettyCash(pettyCashDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PettyCashDTO>> getAllPettyCash() {
        return ResponseEntity.ok(pettyCashService.getAllPettyCash());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PettyCashDTO> updatePettyCash(@PathVariable Long id, @RequestBody PettyCashDTO pettyCashDTO) {
        return ResponseEntity.ok(pettyCashService.updatePettyCash(id, pettyCashDTO));
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<PettyCashDTO> updatePettyCashWithFile(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file,
        @RequestParam("pettyCashData") String pettyCashDataJson
    ) {
        try {
            PettyCashDTO pettyCashDTO = objectMapper.readValue(pettyCashDataJson, PettyCashDTO.class);
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
            pettyCashDTO.setDocumentPath(downloadUri);
            return ResponseEntity.ok(pettyCashService.updatePettyCash(id, pettyCashDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePettyCash(@PathVariable Long id) {
        pettyCashService.deletePettyCash(id);
        return ResponseEntity.ok().build();
    }
}
