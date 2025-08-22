package com.example.storemanagementbackend.service;


import com.example.storemanagementbackend.dto.ElectricBillsDTO;


import java.util.List;


public interface ElectricBillsService {
    ElectricBillsDTO createElectricBill(ElectricBillsDTO dto);
    List<ElectricBillsDTO> getAllElectricBills();
    ElectricBillsDTO updateElectricBill(Long id, ElectricBillsDTO dto);
    void deleteElectricBill(Long id);
}
