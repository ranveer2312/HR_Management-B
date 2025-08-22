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
@RequestMapping("/api/uploads")
@CrossOrigin(origins = {"https://idmstiranga.online", "http://idmstiranga.online", "http://localhost:3000"})
public class FileController {

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            String decodedFilename = URLDecoder.decode(filename, StandardCharsets.UTF_8);
            File uploadsDir = new File("uploads/");
            File file = findFile(uploadsDir, decodedFilename);
            
            if (file == null || !file.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            Resource resource = new FileSystemResource(file);
            String contentType = getContentType(file.getName());
            
            String safeFilename = file.getName().contains("_") ? 
                file.getName().substring(file.getName().indexOf("_") + 1) : file.getName();
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header("Content-Disposition", "attachment; filename=\"" + safeFilename + "\"")
                    .header("Content-Security-Policy", "default-src 'self'")
                    .header("X-Content-Type-Options", "nosniff")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    private File findFile(File uploadsDir, String filename) {
        if (!uploadsDir.exists()) return null;
        
        // Try direct match first
        File directFile = new File(uploadsDir, filename);
        if (directFile.exists()) return directFile;
        
        // Search for UUID-prefixed files and exact matches
        File[] files = uploadsDir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile() && 
                    (f.getName().equals(filename) || 
                     f.getName().endsWith("_" + filename) ||
                     f.getName().contains(filename))) {
                    return f;
                }
            }
        }
        return null;
    }
    
    private String getContentType(String filename) {
        if (filename.lastIndexOf('.') == -1) return "application/octet-stream";
        
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "pdf": return "application/pdf";
            case "doc": return "application/msword";
            case "docx": return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls": return "application/vnd.ms-excel";
            case "xlsx": return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt": return "application/vnd.ms-powerpoint";
            case "pptx": return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "txt": return "text/plain";
            case "csv": return "text/csv";
            case "rtf": return "application/rtf";
            case "jpg":
            case "jpeg": return "image/jpeg";
            case "png": return "image/png";
            case "gif": return "image/gif";
            case "bmp": return "image/bmp";
            case "svg": return "image/svg+xml";
            case "zip": return "application/zip";
            case "rar": return "application/x-rar-compressed";
            case "7z": return "application/x-7z-compressed";
            default: return "application/octet-stream";
        }
    }
}
