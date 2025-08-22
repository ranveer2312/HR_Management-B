
package com.example.storemanagementbackend.service.impl;


import com.example.storemanagementbackend.dto.InternetBillsDTO;
import com.example.storemanagementbackend.model.InternetBills;
import com.example.storemanagementbackend.repository.InternetBillsRepository;
import com.example.storemanagementbackend.service.InternetBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class InternetBillsServiceImpl implements InternetBillsService {
    @Autowired
    private InternetBillsRepository internetBillsRepository;


    @Override
    public InternetBillsDTO createInternetBill(InternetBillsDTO dto) {
        try {
            InternetBills internetBill = new InternetBills();
            internetBill.setAccountNo(dto.getAccountNo());
            internetBill.setPaymentDate(dto.getPaymentDate());
            internetBill.setDate(dto.getDate());
            internetBill.setPaymentMode(dto.getPaymentMode());
            internetBill.setMonth(dto.getMonth());
            internetBill.setAmount(dto.getPayment());
            internetBill.setPayment(dto.getPayment());
            internetBill.setRemarks(dto.getRemarks());
            internetBill.setDocumentPath(dto.getDocumentPath());
            return convertToDTO(internetBillsRepository.save(internetBill));
        } catch (Exception e) {
            throw new RuntimeException("Error creating internet bill: " + e.getMessage(), e);
        }
    }


    @Override
    public List<InternetBillsDTO> getAllInternetBills() {
        try {
            System.out.println("InternetBillsServiceImpl: getAllInternetBills called");
            List<InternetBills> entities = internetBillsRepository.findAll();
            System.out.println("InternetBillsServiceImpl: Found " + entities.size() + " entities from repository");
            
            List<InternetBillsDTO> dtos = entities.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            System.out.println("InternetBillsServiceImpl: Converted to " + dtos.size() + " DTOs");
            return dtos;
        } catch (Exception e) {
            System.err.println("InternetBillsServiceImpl: Error in getAllInternetBills: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching internet bills: " + e.getMessage(), e);
        }
    }


    @Override
    public InternetBillsDTO updateInternetBill(Long id, InternetBillsDTO dto) {
        try {
            InternetBills internetBill = internetBillsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Internet Bill not found"));
            internetBill.setAccountNo(dto.getAccountNo());
            internetBill.setPaymentDate(dto.getPaymentDate());
            internetBill.setDate(dto.getDate());
            internetBill.setPaymentMode(dto.getPaymentMode());
            internetBill.setMonth(dto.getMonth());
            internetBill.setAmount(dto.getPayment());
            internetBill.setPayment(dto.getPayment());
            internetBill.setRemarks(dto.getRemarks());
            internetBill.setDocumentPath(dto.getDocumentPath());
            return convertToDTO(internetBillsRepository.save(internetBill));
        } catch (Exception e) {
            throw new RuntimeException("Error updating internet bill: " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteInternetBill(Long id) {
        internetBillsRepository.deleteById(id);
    }


    private InternetBillsDTO convertToDTO(InternetBills internetBill) {
        try {
            InternetBillsDTO dto = new InternetBillsDTO();
            dto.setId(internetBill.getId());
            dto.setAccountNo(internetBill.getAccountNo());
            dto.setPaymentDate(internetBill.getPaymentDate());
            dto.setDate(internetBill.getDate());
            dto.setPaymentMode(internetBill.getPaymentMode());
            dto.setMonth(internetBill.getMonth());
            // Use payment field if available, otherwise use amount
            dto.setPayment(internetBill.getPayment() != null ? internetBill.getPayment() : internetBill.getAmount());
            dto.setRemarks(internetBill.getRemarks());
            
            // Extract original filename from document path
            String documentPath = internetBill.getDocumentPath();
            if (documentPath != null && documentPath.contains("_")) {
                dto.setDocumentPath(documentPath.substring(documentPath.indexOf("_") + 1));
            } else {
                dto.setDocumentPath(documentPath);
            }
            
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error converting internet bill to DTO: " + e.getMessage(), e);
        }
    }
}


