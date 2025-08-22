package com.example.storemanagementbackend.controller;
 
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
 
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
 
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://idmstiranga.online", "http://idmstiranga.online", "http://localhost:3000"})
public class FileUploadController {
 
    private final String UPLOAD_DIR = "uploads";
 
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        return uploadFilesInternal(files);
    }

    @PostMapping("/upload-internet-bill")
    public ResponseEntity<Map<String, Object>> uploadInternetBillFiles(@RequestParam("files") MultipartFile[] files) {
        return uploadFilesInternal(files);
    }

    private ResponseEntity<Map<String, Object>> uploadFilesInternal(MultipartFile[] files) {
        try {
            // Create uploads directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
 
            List<Map<String, String>> fileDetails = new ArrayList<>();
           
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Generate unique filename with length control
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String nameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
                    
                    // Truncate filename if too long (keep it under 100 chars for the name part)
                    if (nameWithoutExt.length() > 100) {
                        nameWithoutExt = nameWithoutExt.substring(0, 100);
                    }
                    
                    String uniqueFilename = UUID.randomUUID().toString() + "_" + nameWithoutExt + extension;
                   
                    // Save file
                    Path filePath = Paths.get(UPLOAD_DIR, uniqueFilename);
                    Files.write(filePath, file.getBytes());
                   
                    Map<String, String> fileInfo = new HashMap<>();
                    fileInfo.put("fileName", uniqueFilename);
                    fileInfo.put("originalName", originalFilename);
                    fileInfo.put("filePath", UPLOAD_DIR + "/" + uniqueFilename);
                    fileDetails.add(fileInfo);
                }
            }
 
            Map<String, Object> response = new HashMap<>();
            response.put("files", fileDetails);
            response.put("message", "Files uploaded successfully");
           
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Decode URL-encoded filename
            String decodedFileName = java.net.URLDecoder.decode(fileName, "UTF-8");
            Path filePath = Paths.get(UPLOAD_DIR).resolve(decodedFileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                // Extract original filename from the UUID-prefixed filename
                String originalFileName = decodedFileName;
                if (decodedFileName.contains("_")) {
                    originalFileName = decodedFileName.substring(decodedFileName.indexOf("_") + 1);
                }
                
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        try {
            // Decode URL-encoded filename
            String decodedFileName = java.net.URLDecoder.decode(fileName, "UTF-8");
            Path filePath = Paths.get(UPLOAD_DIR).resolve(decodedFileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                // Determine content type based on file extension
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
    
    @RequestMapping(value = "/files/{fileName}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> headFile(@PathVariable String fileName) {
        try {
            // Decode URL-encoded filename
            String decodedFileName = java.net.URLDecoder.decode(fileName, "UTF-8");
            Path filePath = Paths.get(UPLOAD_DIR).resolve(decodedFileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                // Determine content type based on file extension
                String contentType = getContentType(decodedFileName);
                
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .build();
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
        
        // Document formats
        if (extension.endsWith(".pdf")) return "application/pdf";
        if (extension.endsWith(".doc")) return "application/msword";
        if (extension.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (extension.endsWith(".txt")) return "text/plain";
        if (extension.endsWith(".rtf")) return "application/rtf";
        
        // Image formats
        if (extension.endsWith(".jpg") || extension.endsWith(".jpeg")) return "image/jpeg";
        if (extension.endsWith(".png")) return "image/png";
        if (extension.endsWith(".gif")) return "image/gif";
        if (extension.endsWith(".bmp")) return "image/bmp";
        if (extension.endsWith(".svg")) return "image/svg+xml";
        if (extension.endsWith(".webp")) return "image/webp";
        
        // Spreadsheet formats
        if (extension.endsWith(".xls")) return "application/vnd.ms-excel";
        if (extension.endsWith(".xlsx")) return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (extension.endsWith(".csv")) return "text/csv";
        
        // Presentation formats
        if (extension.endsWith(".ppt")) return "application/vnd.ms-powerpoint";
        if (extension.endsWith(".pptx")) return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        
        // Archive formats
        if (extension.endsWith(".zip")) return "application/zip";
        if (extension.endsWith(".rar")) return "application/vnd.rar";
        if (extension.endsWith(".7z")) return "application/x-7z-compressed";
        if (extension.endsWith(".tar")) return "application/x-tar";
        if (extension.endsWith(".gz")) return "application/gzip";
        
        // Audio formats
        if (extension.endsWith(".mp3")) return "audio/mpeg";
        if (extension.endsWith(".wav")) return "audio/wav";
        if (extension.endsWith(".ogg")) return "audio/ogg";
        if (extension.endsWith(".m4a")) return "audio/mp4";
        
        // Video formats
        if (extension.endsWith(".mp4")) return "video/mp4";
        if (extension.endsWith(".avi")) return "video/x-msvideo";
        if (extension.endsWith(".mov")) return "video/quicktime";
        if (extension.endsWith(".wmv")) return "video/x-ms-wmv";
        if (extension.endsWith(".flv")) return "video/x-flv";
        if (extension.endsWith(".webm")) return "video/webm";
        
        // Web formats
        if (extension.endsWith(".html") || extension.endsWith(".htm")) return "text/html";
        if (extension.endsWith(".css")) return "text/css";
        if (extension.endsWith(".js")) return "application/javascript";
        if (extension.endsWith(".json")) return "application/json";
        if (extension.endsWith(".xml")) return "application/xml";
        
        // Programming files
        if (extension.endsWith(".java")) return "text/x-java-source";
        if (extension.endsWith(".py")) return "text/x-python";
        if (extension.endsWith(".cpp") || extension.endsWith(".c")) return "text/x-c";
        if (extension.endsWith(".php")) return "application/x-httpd-php";
        
        // Default fallback
        return "application/octet-stream";
    }
}
 
