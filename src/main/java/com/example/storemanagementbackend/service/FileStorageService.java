package com.example.storemanagementbackend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface FileStorageService {
    String storeFile(MultipartFile file); // Local upload

    Resource loadFileAsResource(String fileName); // Local download

    Path getFileStorageLocation(String uploadDir);

    String storeFileToCloudinary(MultipartFile file); // Cloudinary upload
}
