package com.example.storemanagementbackend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final String UPLOAD_DIR = "uploads";

    @RequestMapping(value = "/{fileName:.+}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        try {
            String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            Path filePath = Paths.get(UPLOAD_DIR).resolve(decodedFileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                String contentType = getContentType(decodedFileName);
                
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    

    

    
    private String getContentType(String fileName) {
        String extension = fileName.toLowerCase();
        
        if (extension.endsWith(".pdf")) return "application/pdf";
        if (extension.endsWith(".doc")) return "application/msword";
        if (extension.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (extension.endsWith(".txt")) return "text/plain";
        if (extension.endsWith(".jpg") || extension.endsWith(".jpeg")) return "image/jpeg";
        if (extension.endsWith(".png")) return "image/png";
        if (extension.endsWith(".gif")) return "image/gif";
        if (extension.endsWith(".bmp")) return "image/bmp";
        if (extension.endsWith(".svg")) return "image/svg+xml";
        if (extension.endsWith(".webp")) return "image/webp";
        if (extension.endsWith(".xls")) return "application/vnd.ms-excel";
        if (extension.endsWith(".xlsx")) return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (extension.endsWith(".csv")) return "text/csv";
        if (extension.endsWith(".ppt")) return "application/vnd.ms-powerpoint";
        if (extension.endsWith(".pptx")) return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        
        return "application/octet-stream";
    }
}
