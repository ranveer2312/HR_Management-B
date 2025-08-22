package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.dto.MaterialDTO;
import com.example.storemanagementbackend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public List<MaterialDTO> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> getMaterialById(@PathVariable Long id) {
        MaterialDTO material = materialService.getMaterialById(id);
        if (material != null) {
            return ResponseEntity.ok(material);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public MaterialDTO createMaterial(@RequestBody MaterialDTO materialDTO) {
        return materialService.createMaterial(materialDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> updateMaterial(@PathVariable Long id, @RequestBody MaterialDTO materialDTO) {
        MaterialDTO updated = materialService.updateMaterial(id, materialDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
} 
