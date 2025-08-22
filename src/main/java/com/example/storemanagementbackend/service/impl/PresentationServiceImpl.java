package com.example.storemanagementbackend.service.impl;
 
import com.example.storemanagementbackend.dto.PresentationDTO;
import com.example.storemanagementbackend.model.Presentation;
import com.example.storemanagementbackend.repository.PresentationRepository;
import com.example.storemanagementbackend.service.PresentationService;
import com.example.storemanagementbackend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class PresentationServiceImpl implements PresentationService {
 
    @Autowired
    private PresentationRepository presentationRepository;
 
    @Autowired
    private FileStorageService fileStorageService;
 
    @Override
    public List<PresentationDTO> getAllPresentations() {
        return presentationRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    public PresentationDTO getPresentationById(Long id) {
        return presentationRepository.findById(id).map(this::toDTO).orElse(null);
    }
 
    @Override
    public PresentationDTO uploadPresentation(String title, String description, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String originalFileName = file.getOriginalFilename();
        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/presentations/download/")
                .path(fileName)
                .toUriString();
 
        Presentation presentation = new Presentation();
        presentation.setTitle(title);
        presentation.setDescription(description);
        presentation.setFileName(fileName);
        presentation.setOriginalFileName(originalFileName);
        presentation.setFileType(file.getContentType());
        presentation.setSize(file.getSize());
        presentation.setFileDownloadUri(downloadUri);
 
        presentation = presentationRepository.save(presentation);
        return toDTO(presentation);
    }
    @Override
public PresentationDTO updatePresentation(Long id, String title, String description, MultipartFile file) {
    return presentationRepository.findById(id).map(presentation -> {
        presentation.setTitle(title);
        presentation.setDescription(description);
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            String originalFileName = file.getOriginalFilename();
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/presentations/download/")
                    .path(fileName)
                    .toUriString();
            presentation.setFileName(fileName);
            presentation.setOriginalFileName(originalFileName);
            presentation.setFileType(file.getContentType());
            presentation.setSize(file.getSize());
            presentation.setFileDownloadUri(downloadUri);
        }
        Presentation saved = presentationRepository.save(presentation);
        return toDTO(saved);
    }).orElse(null);
}
 
    @Override
    public void deletePresentation(Long id) {
        presentationRepository.deleteById(id);
    }
 
    private PresentationDTO toDTO(Presentation p) {
        return new PresentationDTO(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getFileName(),
                p.getOriginalFileName(),
                p.getFileDownloadUri(),
                p.getFileType(),
                p.getSize()
        );
    }
}
 