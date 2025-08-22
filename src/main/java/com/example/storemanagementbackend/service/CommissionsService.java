package com.example.storemanagementbackend.service;


import com.example.storemanagementbackend.dto.CommissionsDTO;
import com.example.storemanagementbackend.model.Commission;
import com.example.storemanagementbackend.model.Commission.PaymentMode;
import com.example.storemanagementbackend.repository.CommissionsRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommissionsService {
    private final CommissionsRepository commissionsRepository;


    public CommissionsService(CommissionsRepository commissionsRepository) {
        this.commissionsRepository = commissionsRepository;
    }


    public CommissionsDTO createCommission(CommissionsDTO dto) {
        Commission commission = new Commission();
        commission.setFixedTarget(dto.getFixedTarget());
        commission.setRecipient(dto.getRecipient());
        commission.setAchieved(dto.getAchieved());
        commission.setPending(dto.getPending());
        commission.setAmount(dto.getPayment());
        commission.setPayment(dto.getPayment());
        commission.setPaymentMode(PaymentMode.valueOf(dto.getPaymentMode()));
        commission.setRemarks(dto.getRemarks());
        commission.setDate(dto.getDate());
        commission.setDocumentPath(dto.getDocumentPath());
        return convertToDTO(commissionsRepository.save(commission));
    }


    public List<CommissionsDTO> getAllCommissions() {
        return commissionsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public CommissionsDTO updateCommission(Long id, CommissionsDTO dto) {
        Commission commission = commissionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commission not found"));
        commission.setFixedTarget(dto.getFixedTarget());
        commission.setRecipient(dto.getRecipient());
        commission.setAchieved(dto.getAchieved());
        commission.setPending(dto.getPending());
        commission.setAmount(dto.getPayment());
        commission.setPayment(dto.getPayment());
        commission.setPaymentMode(PaymentMode.valueOf(dto.getPaymentMode()));
        commission.setRemarks(dto.getRemarks());
        commission.setDate(dto.getDate());
        commission.setDocumentPath(dto.getDocumentPath());
        return convertToDTO(commissionsRepository.save(commission));
    }


    public void deleteCommission(Long id) {
        commissionsRepository.deleteById(id);
    }


    private CommissionsDTO convertToDTO(Commission commission) {
        CommissionsDTO dto = new CommissionsDTO();
        dto.setId(commission.getId());
        dto.setFixedTarget(commission.getFixedTarget());
        dto.setRecipient(commission.getRecipient());
        dto.setAchieved(commission.getAchieved());
        dto.setPending(commission.getPending());
        dto.setPayment(commission.getPayment());
        dto.setPaymentMode(commission.getPaymentMode().name());
        dto.setRemarks(commission.getRemarks());
        dto.setDate(commission.getDate());
        dto.setDocumentPath(commission.getDocumentPath());
        return dto;
    }
}

