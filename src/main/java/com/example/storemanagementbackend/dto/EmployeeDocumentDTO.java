package com.example.storemanagementbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDocumentDTO {
    private Long id;
    private String employeeId;
    private String documentType;
    private String fileName;
    private String originalFileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public EmployeeDocumentDTO(Long id, String employeeId, String documentType, String fileName, String originalFileName, String fileDownloadUri, String fileType, Long size) {
        this.id = id;
        this.employeeId = employeeId;
        this.documentType = documentType;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public Long getId() { return id; }
    public String getEmployeeId() { return employeeId; }
    public String getDocumentType() { return documentType; }
    public String getFileName() { return fileName; }
    public String getOriginalFileName() { return originalFileName; }
    public String getFileDownloadUri() { return fileDownloadUri; }
    public String getFileType() { return fileType; }
    public Long getSize() { return size; }
}
 
 