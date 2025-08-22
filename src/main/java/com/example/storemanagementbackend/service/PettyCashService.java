package com.example.storemanagementbackend.service;

import com.example.storemanagementbackend.dto.PettyCashDTO;
import com.example.storemanagementbackend.model.PettyCash;
import com.example.storemanagementbackend.repository.PettyCashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PettyCashService {
    private final PettyCashRepository pettyCashRepository;

    @Autowired
    public PettyCashService(PettyCashRepository pettyCashRepository) {
        this.pettyCashRepository = pettyCashRepository;
    }

    public PettyCashDTO createPettyCash(PettyCashDTO dto) {
        PettyCash pettyCash = new PettyCash();
        pettyCash.setItemName(dto.getItemName());
        pettyCash.setPaidTo(dto.getPaidTo());
        pettyCash.setBillNo(dto.getBillNo());
        pettyCash.setAmount(dto.getAmount());
        pettyCash.setPaymentMode(dto.getPaymentMode());
        pettyCash.setPaymentDate(dto.getPaymentDate());
        pettyCash.setRemarks(dto.getRemarks());
        pettyCash.setDocumentPath(dto.getDocumentPath());

        PettyCash savedPettyCash = pettyCashRepository.save(pettyCash);
        return convertToDTO(savedPettyCash);
    }

    public List<PettyCashDTO> getAllPettyCash() {
        return pettyCashRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PettyCashDTO updatePettyCash(Long id, PettyCashDTO dto) {
        PettyCash pettyCash = pettyCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Petty cash entry not found"));

        pettyCash.setItemName(dto.getItemName());
        pettyCash.setPaidTo(dto.getPaidTo());
        pettyCash.setBillNo(dto.getBillNo());
        pettyCash.setAmount(dto.getAmount());
        pettyCash.setPaymentMode(dto.getPaymentMode());
        pettyCash.setPaymentDate(dto.getPaymentDate());
        pettyCash.setRemarks(dto.getRemarks());
        pettyCash.setDocumentPath(dto.getDocumentPath());

        PettyCash updatedPettyCash = pettyCashRepository.save(pettyCash);
        return convertToDTO(updatedPettyCash);
    }

    public void deletePettyCash(Long id) {
        pettyCashRepository.deleteById(id);
    }

    private PettyCashDTO convertToDTO(PettyCash pettyCash) {
        PettyCashDTO dto = new PettyCashDTO();
        dto.setId(pettyCash.getId());
        dto.setItemName(pettyCash.getItemName());
        dto.setPaidTo(pettyCash.getPaidTo());
        dto.setBillNo(pettyCash.getBillNo());
        dto.setAmount(pettyCash.getAmount());
        dto.setPaymentMode(pettyCash.getPaymentMode());
        dto.setPaymentDate(pettyCash.getPaymentDate());
        dto.setRemarks(pettyCash.getRemarks());
        dto.setDocumentPath(pettyCash.getDocumentPath());
        return dto;
    }
}