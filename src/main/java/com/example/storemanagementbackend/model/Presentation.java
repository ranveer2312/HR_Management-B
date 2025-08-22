package com.example.storemanagementbackend.model;
 
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
@Entity
@Table(name = "presentations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String title;
    private String description;
 
    @Column(name = "file_name")
    private String fileName;
 
    @Column(name = "original_file_name")
    private String originalFileName;
 
    @Column(name = "file_type")
    private String fileType;
 
    @Column(name = "file_download_uri")
    private String fileDownloadUri;
 
    @Column(name = "size")
    private Long size;
}
 