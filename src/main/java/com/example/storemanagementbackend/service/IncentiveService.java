package com.example.storemanagementbackend.service;


import com.example.storemanagementbackend.dto.IncentiveDTO;
import com.example.storemanagementbackend.model.Incentive;
import com.example.storemanagementbackend.repository.IncentiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class IncentiveService {
    private final IncentiveRepository incentiveRepository;


    @Autowired
    public IncentiveService(IncentiveRepository incentiveRepository) {
        this.incentiveRepository = incentiveRepository;
    }


    public IncentiveDTO createIncentive(IncentiveDTO dto) {
        Incentive incentive = new Incentive();
        incentive.setFixedTarget(dto.getFixedTarget());
        incentive.setAchieved(dto.getAchieved());
        incentive.setRecipient(dto.getRecipient());
        incentive.setDate(dto.getDate());
        incentive.setPending(dto.getPending());
        incentive.setAmount(dto.getPayment());
        incentive.setPayment(dto.getPayment());
        incentive.setPaymentMode(dto.getPaymentMode());
        incentive.setRemarks(dto.getRemarks());
        incentive.setDocumentPath(dto.getDocumentPath());
       
        Incentive savedIncentive = incentiveRepository.save(incentive);
        return convertToDTO(savedIncentive);
    }


    public List<IncentiveDTO> getAllIncentives() {
        return incentiveRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public IncentiveDTO updateIncentive(Long id, IncentiveDTO dto) {
        Incentive incentive = incentiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incentive not found"));
       
        incentive.setFixedTarget(dto.getFixedTarget());
        incentive.setAchieved(dto.getAchieved());
        incentive.setRecipient(dto.getRecipient());
        incentive.setDate(dto.getDate());
        incentive.setPending(dto.getPending());
        incentive.setAmount(dto.getPayment());
        incentive.setPayment(dto.getPayment());
        incentive.setPaymentMode(dto.getPaymentMode());
        incentive.setRemarks(dto.getRemarks());
        incentive.setDocumentPath(dto.getDocumentPath());
       
        Incentive updatedIncentive = incentiveRepository.save(incentive);
        return convertToDTO(updatedIncentive);
    }


    public void deleteIncentive(Long id) {
        incentiveRepository.deleteById(id);
    }


    private IncentiveDTO convertToDTO(Incentive incentive) {
        IncentiveDTO dto = new IncentiveDTO();
        dto.setId(incentive.getId());
        dto.setFixedTarget(incentive.getFixedTarget());
        dto.setAchieved(incentive.getAchieved());
        dto.setRecipient(incentive.getRecipient());
        dto.setDate(incentive.getDate());
        dto.setPending(incentive.getPending());
        dto.setPayment(incentive.getPayment());
        dto.setPaymentMode(incentive.getPaymentMode());
        dto.setRemarks(incentive.getRemarks());
        dto.setDocumentPath(incentive.getDocumentPath());
        return dto;
    }
}

