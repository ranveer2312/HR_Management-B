package com.example.storemanagementbackend.dto;
 
 
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationDTO {
    private Long id;
    private String title;
    private String description;
    private String fileName;
    private String originalFileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;
}
 