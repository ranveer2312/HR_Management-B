package com.example.storemanagementbackend.service;


import com.example.storemanagementbackend.dto.RentDTO;
import com.example.storemanagementbackend.model.Rent;
import com.example.storemanagementbackend.repository.RentRepository;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RentService {
    private final RentRepository rentRepository;


    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }


    public RentDTO createRent(RentDTO dto) {
        Rent rent = new Rent();
        rent.setInvoiceNo(dto.getInvoiceNo());
        rent.setOwnerName(dto.getOwnerName());
        rent.setAmount(dto.getAmount());
        rent.setDate(java.time.LocalDate.parse(dto.getDate()));
        rent.setPaymentDate(java.time.LocalDate.parse(dto.getPaymentDate()));
        rent.setPaymentMode(dto.getPaymentMode());
        rent.setTds(dto.getTds());
        rent.setGstAmount(dto.getGstAmount());
        rent.setTaxableAmount(dto.getTaxableAmount());
        rent.setPayment(dto.getFinalPayment() != null ? dto.getFinalPayment() : BigDecimal.ZERO);
        rent.setFinalPayment(dto.getFinalPayment() != null ? dto.getFinalPayment() : BigDecimal.ZERO);
        rent.setRemarks(dto.getRemarks());
        rent.setDescription(dto.getDescription());
        rent.setDocumentPath(dto.getDocumentPath());
        return convertToDTO(rentRepository.save(rent));
    }


    public List<RentDTO> getAllRents() {
        return rentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public RentDTO updateRent(Long id, RentDTO dto) {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rent not found"));
        rent.setInvoiceNo(dto.getInvoiceNo());
        rent.setOwnerName(dto.getOwnerName());
        rent.setAmount(dto.getAmount());
        rent.setDate(java.time.LocalDate.parse(dto.getDate()));
        rent.setPaymentDate(java.time.LocalDate.parse(dto.getPaymentDate()));
        rent.setPaymentMode(dto.getPaymentMode());
        rent.setTds(dto.getTds());
        rent.setGstAmount(dto.getGstAmount());
        rent.setTaxableAmount(dto.getTaxableAmount());
        rent.setPayment(dto.getFinalPayment() != null ? dto.getFinalPayment() : BigDecimal.ZERO);
        rent.setFinalPayment(dto.getFinalPayment() != null ? dto.getFinalPayment() : BigDecimal.ZERO);
        rent.setRemarks(dto.getRemarks());
        rent.setDescription(dto.getDescription());
        rent.setDocumentPath(dto.getDocumentPath());
        return convertToDTO(rentRepository.save(rent));
    }


    public void deleteRent(Long id) {
        rentRepository.deleteById(id);
    }


    private RentDTO convertToDTO(Rent rent) {
        RentDTO dto = new RentDTO();
        dto.setId(rent.getId());
        dto.setInvoiceNo(rent.getInvoiceNo());
        dto.setOwnerName(rent.getOwnerName());
        dto.setAmount(rent.getAmount());
        dto.setDate(rent.getDate().toString());
        dto.setPaymentDate(rent.getPaymentDate().toString());
        dto.setPaymentMode(rent.getPaymentMode());
        dto.setTds(rent.getTds());
        dto.setGstAmount(rent.getGstAmount());
        dto.setTaxableAmount(rent.getTaxableAmount());
        dto.setFinalPayment(rent.getFinalPayment());
        dto.setRemarks(rent.getRemarks());
        dto.setDescription(rent.getDescription());
        dto.setDocumentPath(rent.getDocumentPath());
        return dto;
    }
}

