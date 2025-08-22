package com.example.storemanagementbackend.service;


import com.example.storemanagementbackend.dto.SIMBillsDTO;
import com.example.storemanagementbackend.model.SIMBills;
import com.example.storemanagementbackend.repository.SIMBillsRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class SIMBillsService {
    private final SIMBillsRepository simBillsRepository;


    public SIMBillsService(SIMBillsRepository simBillsRepository) {
        this.simBillsRepository = simBillsRepository;
    }


    public SIMBillsDTO createSIMBill(SIMBillsDTO dto) {
        SIMBills simBill = new SIMBills();
        simBill.setAccountNo(dto.getAccountNo());
        simBill.setPaymentDate(dto.getPaymentDate());
        simBill.setPaymentMode(dto.getPaymentMode());
        simBill.setMonth(dto.getMonth());
        simBill.setAmount(dto.getPayment());
        simBill.setPayment(dto.getPayment());
        simBill.setRemarks(dto.getRemarks());
        simBill.setDocumentPath(dto.getDocumentPath());
        simBill.setDate(dto.getDate());
        return convertToDTO(simBillsRepository.save(simBill));
    }


    public List<SIMBillsDTO> getAllSIMBills() {
        return simBillsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public SIMBillsDTO updateSIMBill(Long id, SIMBillsDTO dto) {
        SIMBills simBill = simBillsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SIM Bill not found"));
        simBill.setAccountNo(dto.getAccountNo());
        simBill.setPaymentDate(dto.getPaymentDate());
        simBill.setPaymentMode(dto.getPaymentMode());
        simBill.setMonth(dto.getMonth());
        simBill.setAmount(dto.getPayment());
        simBill.setPayment(dto.getPayment());
        simBill.setRemarks(dto.getRemarks());
        simBill.setDocumentPath(dto.getDocumentPath());
        simBill.setDate(dto.getDate());
        return convertToDTO(simBillsRepository.save(simBill));
    }


    public void deleteSIMBill(Long id) {
        simBillsRepository.deleteById(id);
    }


    private SIMBillsDTO convertToDTO(SIMBills simBill) {
        SIMBillsDTO dto = new SIMBillsDTO();
        dto.setId(simBill.getId());
        dto.setAccountNo(simBill.getAccountNo());
        dto.setPaymentDate(simBill.getPaymentDate());
        dto.setPaymentMode(simBill.getPaymentMode());
        dto.setMonth(simBill.getMonth());
        dto.setPayment(simBill.getPayment());
        dto.setRemarks(simBill.getRemarks());
        dto.setDocumentPath(simBill.getDocumentPath());
        dto.setDate(simBill.getDate());
        return dto;
    }
}

