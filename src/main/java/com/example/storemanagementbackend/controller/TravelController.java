package com.example.storemanagementbackend.controller;


import com.example.storemanagementbackend.dto.TravelDTO;
import com.example.storemanagementbackend.service.FileStorageService;
import com.example.storemanagementbackend.service.TravelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;


@RestController
@RequestMapping("/api/travel")
public class TravelController {
    private final TravelService travelService;


    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ObjectMapper objectMapper;


    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }


    @PostMapping
    public ResponseEntity<TravelDTO> createTravel(@RequestBody TravelDTO travelDTO) {
        return ResponseEntity.ok(travelService.createTravel(travelDTO));
    }


    @PostMapping("/upload")
    public ResponseEntity<TravelDTO> createTravelWithFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("travelData") String travelDataJson
    ) {
        try {
            TravelDTO travelDTO = objectMapper.readValue(travelDataJson, TravelDTO.class);
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
            travelDTO.setDocumentPath(downloadUri);
            return ResponseEntity.ok(travelService.createTravel(travelDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<TravelDTO>> getAllTravels() {
        return ResponseEntity.ok(travelService.getAllTravels());
    }


    @PutMapping("/{id}")
    public ResponseEntity<TravelDTO> updateTravel(@PathVariable Long id, @RequestBody TravelDTO travelDTO) {
        return ResponseEntity.ok(travelService.updateTravel(id, travelDTO));
    }


    @PostMapping("/upload/{id}")
    public ResponseEntity<TravelDTO> updateTravelWithFile(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file,
        @RequestParam("travelData") String travelDataJson
    ) {
        try {
            TravelDTO travelDTO = objectMapper.readValue(travelDataJson, TravelDTO.class);
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
            travelDTO.setDocumentPath(downloadUri);
            return ResponseEntity.ok(travelService.updateTravel(id, travelDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravel(@PathVariable Long id) {
        travelService.deleteTravel(id);
        return ResponseEntity.ok().build();
    }
}

