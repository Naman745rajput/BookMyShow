package com.naman.bms.service;


import com.naman.bms.dto.PaymentDto;
import com.naman.bms.exception.ResourceNotFoundException;
import com.naman.bms.model.Payment;
import com.naman.bms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentDto makePayment(PaymentDto paymentDto) {

        Payment payment = new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentMethod(paymentDto.getPaymentMethod());
        payment.setStatus("SUCCESS");   // mock success
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentTime(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);
        return mapToDto(savedPayment);
    }

    public PaymentDto getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found with id: " + id));

        return mapToDto(payment);
    }

    public PaymentDto getPaymentByTransactionId(String transactionId) {

        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with transactionId: " + transactionId));

        return mapToDto(payment);
    }

    private PaymentDto mapToDto(Payment payment) {

        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentTime(payment.getPaymentTime());
        return dto;
    }
}
