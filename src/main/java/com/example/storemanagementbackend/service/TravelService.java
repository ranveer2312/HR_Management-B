package com.example.storemanagementbackend.service;


import com.example.storemanagementbackend.dto.TravelDTO;
import com.example.storemanagementbackend.model.Travel;
import com.example.storemanagementbackend.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class TravelService {
    private final TravelRepository travelRepository;


    @Autowired
    public TravelService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }


    public TravelDTO createTravel(TravelDTO dto) {
        Travel travel = new Travel();
        travel.setVendor(dto.getVendor());
        travel.setFromDate(dto.getFromDate());
        travel.setToDate(dto.getToDate());
        travel.setNoOfDays(dto.getNoOfDays());
        travel.setAmount(dto.getAdvancePay());
        travel.setAdvancePay(dto.getAdvancePay());
        travel.setPaymentMode(dto.getPaymentMode());
        travel.setPaymentDate(dto.getPaymentDate());
        travel.setRemarks(dto.getRemarks());
        travel.setDocumentPath(dto.getDocumentPath());
        travel.setDate(dto.getFromDate());
       
        Travel savedTravel = travelRepository.save(travel);
        return convertToDTO(savedTravel);
    }


    public List<TravelDTO> getAllTravels() {
        return travelRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public TravelDTO updateTravel(Long id, TravelDTO dto) {
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel not found"));
       
        travel.setVendor(dto.getVendor());
        travel.setFromDate(dto.getFromDate());
        travel.setToDate(dto.getToDate());
        travel.setNoOfDays(dto.getNoOfDays());
        travel.setAmount(dto.getAdvancePay());
        travel.setAdvancePay(dto.getAdvancePay());
        travel.setPaymentMode(dto.getPaymentMode());
        travel.setPaymentDate(dto.getPaymentDate());
        travel.setRemarks(dto.getRemarks());
        travel.setDocumentPath(dto.getDocumentPath());
        travel.setDate(dto.getFromDate());
       
        Travel updatedTravel = travelRepository.save(travel);
        return convertToDTO(updatedTravel);
    }


    public void deleteTravel(Long id) {
        travelRepository.deleteById(id);
    }


    private TravelDTO convertToDTO(Travel travel) {
        TravelDTO dto = new TravelDTO();
        dto.setId(travel.getId());
        dto.setVendor(travel.getVendor());
        dto.setFromDate(travel.getFromDate());
        dto.setToDate(travel.getToDate());
        dto.setNoOfDays(travel.getNoOfDays());
        dto.setAdvancePay(travel.getAdvancePay());
        dto.setPaymentMode(travel.getPaymentMode());
        dto.setPaymentDate(travel.getPaymentDate());
        dto.setRemarks(travel.getRemarks());
        dto.setDocumentPath(travel.getDocumentPath());
        dto.setDate(travel.getDate());
        return dto;
    }
}

