package com.example.storemanagementbackend.service;


import com.example.storemanagementbackend.dto.InternetBillsDTO;


import java.util.List;


public interface InternetBillsService {
    InternetBillsDTO createInternetBill(InternetBillsDTO dto);
    List<InternetBillsDTO> getAllInternetBills();
    InternetBillsDTO updateInternetBill(Long id, InternetBillsDTO dto);
    void deleteInternetBill(Long id);
}