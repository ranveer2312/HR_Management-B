package com.example.storemanagementbackend.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/files")
public class ApiFilesController {

    private final String UPLOAD_DIR = "uploads";

    @RequestMapping(value = "/{fileName:.+}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        try {
            String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            File file = new File(UPLOAD_DIR, decodedFileName);
            
            if (file.exists() && file.isFile()) {
                Resource resource = new FileSystemResource(file);
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
        
        return "application/octet-stream";
    }
}
