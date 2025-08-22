package com.example.storemanagementbackend.service;
 
import com.example.storemanagementbackend.dto.PresentationDTO;
import org.springframework.web.multipart.MultipartFile;
 
import java.util.List;
 
public interface PresentationService {
    List<PresentationDTO> getAllPresentations();
    PresentationDTO getPresentationById(Long id);
    PresentationDTO uploadPresentation(String title, String description, MultipartFile file);
    PresentationDTO updatePresentation(Long id, String title, String description, MultipartFile file);
    void deletePresentation(Long id);
}
 