package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.dto.MaterialDTO;
import java.util.List;

public interface MaterialService {
    List<MaterialDTO> getAllMaterials();
    MaterialDTO getMaterialById(Long id);
    MaterialDTO createMaterial(MaterialDTO materialDTO);
    MaterialDTO updateMaterial(Long id, MaterialDTO materialDTO);
    void deleteMaterial(Long id);
} 