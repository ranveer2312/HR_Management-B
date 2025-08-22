package com.example.storemanagementbackend.service.impl;

import com.example.storemanagementbackend.dto.GstCalculationRequest;
import com.example.storemanagementbackend.dto.GstCalculationResponse;
import com.example.storemanagementbackend.service.GstService;
import org.springframework.stereotype.Service;

@Service
public class GstServiceImpl implements GstService {

    @Override
    public GstCalculationResponse calculateGst(GstCalculationRequest request) {
        double amount = request.getAmount();
        double rate = request.getGstRate();
        String type = request.getCalculationType();

        double gstAmount = 0;
        double baseAmount = 0;
        double totalAmount = 0;

        if ("INCLUSIVE".equalsIgnoreCase(type)) {
            baseAmount = amount / (1 + rate / 100);
            gstAmount = amount - baseAmount;
            totalAmount = amount;
        } else { // EXCLUSIVE
            gstAmount = (amount * rate) / 100;
            baseAmount = amount;
            totalAmount = amount + gstAmount;
        }

        double cgst = gstAmount / 2;
        double sgst = gstAmount / 2;
        double igst = gstAmount;

        GstCalculationResponse response = new GstCalculationResponse();
        response.setBaseAmount(baseAmount);
        response.setGstAmount(gstAmount);
        response.setTotalAmount(totalAmount);
        response.setCgst(cgst);
        response.setSgst(sgst);
        response.setIgst(igst);

        return response;
    }
}

