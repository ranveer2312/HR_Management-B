package com.example.storemanagementbackend.service.impl;

import com.example.storemanagementbackend.dto.MaterialDTO;
import com.example.storemanagementbackend.model.Material;
import com.example.storemanagementbackend.repository.MaterialRepository;
import com.example.storemanagementbackend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setId(material.getId());
        dto.setName(material.getName());
        dto.setPartNumber(material.getPartNumber());
        dto.setReturnDate(material.getReturnDate());
        dto.setQuantity(material.getQuantity());
        dto.setType(material.getType());
        dto.setCollectDate(material.getCollectDate());
        dto.setPersonName(material.getPersonName());
        dto.setDepartment(material.getDepartment());
        dto.setRemarks(material.getRemarks());
        return dto;
    }

    private Material convertToEntity(MaterialDTO dto) {
        Material material = new Material();
        material.setId(dto.getId());
        material.setName(dto.getName());
        material.setPartNumber(dto.getPartNumber());
        material.setReturnDate(dto.getReturnDate());
        material.setQuantity(dto.getQuantity());
        material.setType(dto.getType());
        material.setCollectDate(dto.getCollectDate());
        material.setPersonName(dto.getPersonName());
        material.setDepartment(dto.getDepartment());
        material.setRemarks(dto.getRemarks());
        return material;
    }

    @Override
    public List<MaterialDTO> getAllMaterials() {
        return materialRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public MaterialDTO getMaterialById(Long id) {
        Optional<Material> material = materialRepository.findById(id);
        return material.map(this::convertToDTO).orElse(null);
    }

    @Override
    public MaterialDTO createMaterial(MaterialDTO materialDTO) {
        Material material = convertToEntity(materialDTO);
        Material saved = materialRepository.save(material);
        return convertToDTO(saved);
    }

    @Override
    public MaterialDTO updateMaterial(Long id, MaterialDTO materialDTO) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            material.setName(materialDTO.getName());
            material.setPartNumber(materialDTO.getPartNumber());
            material.setReturnDate(materialDTO.getReturnDate());
            material.setQuantity(materialDTO.getQuantity());
            material.setType(materialDTO.getType());
            material.setCollectDate(materialDTO.getCollectDate());
            material.setPersonName(materialDTO.getPersonName());
            material.setDepartment(materialDTO.getDepartment());
            material.setRemarks(materialDTO.getRemarks());
            Material updated = materialRepository.save(material);
            return convertToDTO(updated);
        }
        return null;
    }

    @Override
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }
} 