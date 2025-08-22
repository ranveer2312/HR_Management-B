package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.EmployeeDocumentDTO;
import com.example.storemanagementbackend.model.EmployeeDocument;
import com.example.storemanagementbackend.repository.EmployeeDocumentRepository;
import com.example.storemanagementbackend.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hr")
@CrossOrigin(origins = "https://hr-management-f.vercel.app")
public class EmployeeDocumentController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EmployeeDocumentRepository documentRepository;

    // Get all documents
    @GetMapping("/documents")
    public ResponseEntity<List<EmployeeDocumentDTO>> getAllDocuments() {
        List<EmployeeDocument> documents = documentRepository.findAll();
        List<EmployeeDocumentDTO> documentDTOs = documents.stream()
                .map(doc -> new EmployeeDocumentDTO(
                        doc.getId(),
                        doc.getEmployeeId(),
                        doc.getDocumentType(),
                        doc.getFileName(),
                        doc.getOriginalFileName(),
                        doc.getFileDownloadUri(),
                        doc.getFileType(),
                        doc.getSize()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(documentDTOs, HttpStatus.OK);
    }

    // Get documents by employee ID
    @GetMapping("/documents/employee/{employeeId}")
    public ResponseEntity<List<EmployeeDocumentDTO>> getDocumentsByEmployeeId(@PathVariable String employeeId) {
        List<EmployeeDocument> documents = documentRepository.findByEmployeeId(employeeId);
        List<EmployeeDocumentDTO> documentDTOs = documents.stream()
                .map(doc -> new EmployeeDocumentDTO(
                        doc.getId(),
                        doc.getEmployeeId(),
                        doc.getDocumentType(),
                        doc.getFileName(),
                        doc.getOriginalFileName(),
                        doc.getFileDownloadUri(),
                        doc.getFileType(),
                        doc.getSize()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(documentDTOs, HttpStatus.OK);
    }

    // Get documents by document type
    @GetMapping("/documents/type/{documentType}")
    public ResponseEntity<List<EmployeeDocumentDTO>> getDocumentsByType(@PathVariable String documentType) {
        List<EmployeeDocument> documents = documentRepository.findByDocumentType(documentType.toUpperCase());
        List<EmployeeDocumentDTO> documentDTOs = documents.stream()
                .map(doc -> new EmployeeDocumentDTO(
                        doc.getId(),
                        doc.getEmployeeId(),
                        doc.getDocumentType(),
                        doc.getFileName(),
                        doc.getOriginalFileName(),
                        doc.getFileDownloadUri(),
                        doc.getFileType(),
                        doc.getSize()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(documentDTOs, HttpStatus.OK);
    }

    // Get document by ID
    @GetMapping("/documents/{id}")
    public ResponseEntity<EmployeeDocumentDTO> getDocumentById(@PathVariable Long id) {
        Optional<EmployeeDocument> document = documentRepository.findById(id);
        if (document.isPresent()) {
            EmployeeDocument doc = document.get();
            EmployeeDocumentDTO dto = new EmployeeDocumentDTO(
                    doc.getId(),
                    doc.getEmployeeId(),
                    doc.getDocumentType(),
                    doc.getFileName(),
                    doc.getOriginalFileName(),
                    doc.getFileDownloadUri(),
                    doc.getFileType(),
                    doc.getSize());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/upload/{docType}/{employeeId}", method = { RequestMethod.POST, RequestMethod.PUT })
    public ResponseEntity<EmployeeDocumentDTO> uploadOrUpdateDocument(
            @PathVariable String docType,
            @PathVariable String employeeId,
            @RequestParam("file") MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        String originalFileName = file.getOriginalFilename();
        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/hr/download/")
                .path(fileName)
                .toUriString();

        // Check if document exists for this employee and docType
        List<EmployeeDocument> docs = documentRepository.findByEmployeeIdAndDocumentType(employeeId,
                docType.toUpperCase());
        EmployeeDocument document;
        if (!docs.isEmpty()) {
            // Update existing document
            document = docs.get(0);
            document.setFileName(fileName);
            document.setOriginalFileName(originalFileName);
            document.setFileType(file.getContentType());
            document.setSize(file.getSize());
            document.setFileDownloadUri(downloadUri);
        } else {
            // Create new document
            document = new EmployeeDocument();
            document.setEmployeeId(employeeId);
            document.setDocumentType(docType.toUpperCase());
            document.setFileName(fileName);
            document.setOriginalFileName(originalFileName);
            document.setFileType(file.getContentType());
            document.setSize(file.getSize());
            document.setFileDownloadUri(downloadUri);
        }

        document = documentRepository.save(document);

        EmployeeDocumentDTO dto = new EmployeeDocumentDTO(
                document.getId(),
                document.getEmployeeId(),
                document.getDocumentType(),
                document.getFileName(),
                document.getOriginalFileName(),
                document.getFileDownloadUri(),
                document.getFileType(),
                document.getSize());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable String fileName,
            HttpServletRequest request) {

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
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

    @GetMapping("/view/{fileName:.+}")
    public ResponseEntity<Resource> viewDocument(
            @PathVariable String fileName,
            HttpServletRequest request) {

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = getContentType(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable Long id) {
        Optional<EmployeeDocument> document = documentRepository.findById(id);
        if (document.isPresent()) {
            documentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/download/{employeeId}/{fileType}")
    public ResponseEntity<Resource> downloadDocumentByEmployeeIdAndType(
            @PathVariable String employeeId,
            @PathVariable String fileType,
            HttpServletRequest request) {
        List<EmployeeDocument> docs = documentRepository.findByEmployeeIdAndDocumentType(employeeId,
                fileType.toUpperCase());
        if (docs.isEmpty()) {
            System.out.println("No document found for employeeId: " + employeeId + ", fileType: " + fileType);
            return ResponseEntity.notFound().build();
        }
        EmployeeDocument doc = docs.get(0);
        Resource resource;
        try {
            resource = fileStorageService.loadFileAsResource(doc.getFileName());
            if (resource == null || !resource.exists()) {
                System.out.println("File not found: " + doc.getFileName());
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @GetMapping("/view/{employeeId}/{fileType}")
    public ResponseEntity<Resource> viewDocumentByEmployeeIdAndType(
            @PathVariable String employeeId,
            @PathVariable String fileType,
            HttpServletRequest request) {
        List<EmployeeDocument> docs = documentRepository.findByEmployeeIdAndDocumentType(employeeId,
                fileType.toUpperCase());
        if (docs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EmployeeDocument doc = docs.get(0);
        Resource resource;
        try {
            resource = fileStorageService.loadFileAsResource(doc.getFileName());
            if (resource == null || !resource.exists()) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentType = getContentType(doc.getFileName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "txt":
                return "text/plain";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return "application/octet-stream";
        }
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity<EmployeeDocumentDTO> updateDocument(@PathVariable Long id,
            @RequestBody EmployeeDocumentDTO dto) {
        Optional<EmployeeDocument> optionalDoc = documentRepository.findById(id);
        if (optionalDoc.isPresent()) {
            EmployeeDocument doc = optionalDoc.get();
            doc.setEmployeeId(dto.getEmployeeId());
            doc.setDocumentType(dto.getDocumentType());
            doc.setFileName(dto.getFileName());
            doc.setOriginalFileName(dto.getOriginalFileName());
            doc.setFileDownloadUri(dto.getFileDownloadUri());
            doc.setFileType(dto.getFileType());
            doc.setSize(dto.getSize());
            documentRepository.save(doc);
            EmployeeDocumentDTO updatedDto = new EmployeeDocumentDTO(
                    doc.getId(),
                    doc.getEmployeeId(),
                    doc.getDocumentType(),
                    doc.getFileName(),
                    doc.getOriginalFileName(),
                    doc.getFileDownloadUri(),
                    doc.getFileType(),
                    doc.getSize());
            return ResponseEntity.ok(updatedDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/upload/photo/{employeeId}")
    public ResponseEntity<String> uploadEmployeePhoto(
            @PathVariable String employeeId,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String fileName = fileStorageService.storeFile(file);
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/uploads/")
                    .path(fileName)
                    .toUriString();

            EmployeeDocument document = new EmployeeDocument();
            document.setEmployeeId(employeeId);
            document.setDocumentType("PHOTO");
            document.setFileName(fileName);
            document.setOriginalFileName(file.getOriginalFilename());
            document.setFileType(file.getContentType());
            document.setSize(file.getSize());
            document.setFileDownloadUri(downloadUri);

            documentRepository.save(document);
            return ResponseEntity.ok("Photo uploaded successfully: " + downloadUri);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Photo upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/create-profile/{employeeId}")
    public ResponseEntity<String> createProfileDocument(@PathVariable String employeeId) {
        EmployeeDocument document = new EmployeeDocument();
        document.setEmployeeId(employeeId);
        document.setDocumentType("PROFILE");
        document.setFileName("1754547212753_degree_certificate.jpeg");
        document.setOriginalFileName("degree_certificate.jpeg");
        document.setFileType("image/jpeg");
        document.setSize(0L);
        document.setFileDownloadUri("/api/hr/download/1754547212753_degree_certificate.jpeg");
        documentRepository.save(document);
        return ResponseEntity.ok("PROFILE document created for " + employeeId);
    }

    @RequestMapping(value = "/upload-multi/{docType}/{employeeId}", method = { RequestMethod.POST, RequestMethod.PUT })
    public ResponseEntity<List<EmployeeDocumentDTO>> uploadMultipleDocuments(
            @PathVariable String docType,
            @PathVariable String employeeId,
            @RequestParam("files") List<MultipartFile> files) {

        List<EmployeeDocumentDTO> dtos = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);
            String originalFileName = file.getOriginalFilename();
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/uploads/")
                    .path(fileName)
                    .toUriString();

            EmployeeDocument document = new EmployeeDocument();
            document.setEmployeeId(employeeId);
            document.setDocumentType(docType.toUpperCase());
            document.setFileName(fileName);
            document.setOriginalFileName(originalFileName);
            document.setFileType(file.getContentType());
            document.setSize(file.getSize());
            document.setFileDownloadUri(downloadUri);

            document = documentRepository.save(document);

            EmployeeDocumentDTO dto = new EmployeeDocumentDTO(
                    document.getId(),
                    document.getEmployeeId(),
                    document.getDocumentType(),
                    document.getFileName(),
                    document.getOriginalFileName(),
                    document.getFileDownloadUri(),
                    document.getFileType(),
                    document.getSize());
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);
    }
}
