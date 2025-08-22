package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.PresentationDTO;
import com.example.storemanagementbackend.service.PresentationService;
import com.example.storemanagementbackend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/presentations")
@CrossOrigin(origins = "https://hr-management-f.vercel.app")
public class PresentationController {

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public List<PresentationDTO> getAllPresentations() {
        return presentationService.getAllPresentations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresentationDTO> getPresentationById(@PathVariable Long id) {
        PresentationDTO dto = presentationService.getPresentationById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<PresentationDTO> uploadPresentation(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {
        PresentationDTO dto = presentationService.uploadPresentation(title, description, file);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadPresentation(
            @PathVariable String fileName,
            HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresentationDTO> updatePresentation(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        PresentationDTO updated = presentationService.updatePresentation(id, title, description, file);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePresentation(@PathVariable Long id) {
        presentationService.deletePresentation(id);
        return ResponseEntity.noContent().build();
    }
}
