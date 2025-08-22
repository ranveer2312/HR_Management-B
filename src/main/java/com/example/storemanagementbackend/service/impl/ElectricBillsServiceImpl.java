package com.example.storemanagementbackend.service.impl;


import com.example.storemanagementbackend.dto.ElectricBillsDTO;
import com.example.storemanagementbackend.model.ElectricBills;
import com.example.storemanagementbackend.repository.ElectricBillsRepository;
import com.example.storemanagementbackend.service.ElectricBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class ElectricBillsServiceImpl implements ElectricBillsService {
    private final ElectricBillsRepository electricBillsRepository;


    @Autowired
    public ElectricBillsServiceImpl(ElectricBillsRepository electricBillsRepository) {
        this.electricBillsRepository = electricBillsRepository;
    }


    @Override
    public ElectricBillsDTO createElectricBill(ElectricBillsDTO dto) {
        ElectricBills electricBill = new ElectricBills();
        electricBill.setAccountNo(dto.getAccountNo());
        electricBill.setPaymentDate(dto.getPaymentDate());
        electricBill.setDate(dto.getDate()); // <-- Ensure this is set
        electricBill.setPaymentMode(dto.getPaymentMode()); // ✅ Add this line
        //ectricBill.setPaymentMode(dto.getPaymentMode());
        electricBill.setBillType(dto.getBillType());
        electricBill.setMonth(dto.getMonth());
        electricBill.setPayment(dto.getPayment());
        electricBill.setRemarks(dto.getRemarks());
        electricBill.setDocumentPath(dto.getDocumentPath());
       
        ElectricBills savedElectricBill = electricBillsRepository.save(electricBill);
        return convertToDTO(savedElectricBill);
    }


    @Override
    public List<ElectricBillsDTO> getAllElectricBills() {
        return electricBillsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ElectricBillsDTO updateElectricBill(Long id, ElectricBillsDTO dto) {
        ElectricBills electricBill = electricBillsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Electric Bill not found"));
       
    electricBill.setAccountNo(dto.getAccountNo());
    electricBill.setPaymentDate(dto.getPaymentDate());
    electricBill.setDate(dto.getDate()); // ✅ Make sure the date field is updated
    electricBill.setPaymentMode(dto.getPaymentMode()); // ✅ Ensure this is set
    electricBill.setBillType(dto.getBillType());
    electricBill.setMonth(dto.getMonth());
    electricBill.setPayment(dto.getPayment());
    electricBill.setRemarks(dto.getRemarks());
    electricBill.setDocumentPath(dto.getDocumentPath());
        ElectricBills updatedElectricBill = electricBillsRepository.save(electricBill);
        return convertToDTO(updatedElectricBill);
    }


    @Override
    public void deleteElectricBill(Long id) {
        electricBillsRepository.deleteById(id);
    }


    private ElectricBillsDTO convertToDTO(ElectricBills electricBill) {
        ElectricBillsDTO dto = new ElectricBillsDTO();
        dto.setId(electricBill.getId());
        dto.setAccountNo(electricBill.getAccountNo());
        dto.setPaymentDate(electricBill.getPaymentDate());
        dto.setPaymentMode(electricBill.getPaymentMode());
        dto.setBillType(electricBill.getBillType());
        dto.setMonth(electricBill.getMonth());
        dto.setPayment(electricBill.getPayment());
        dto.setRemarks(electricBill.getRemarks());
        dto.setDocumentPath(electricBill.getDocumentPath());
        dto.setDate(electricBill.getDate());
        return dto;
    }
}



