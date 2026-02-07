package com.naman.bms.controller;

import com.naman.bms.dto.PaymentDto;
import com.naman.bms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDto> makePayment(@RequestBody PaymentDto paymentDto) {
        return new ResponseEntity<>(paymentService.makePayment(paymentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentDto> getPaymentByTransactionId(@PathVariable String transactionId)
    {
        return ResponseEntity.ok(paymentService.getPaymentByTransactionId(transactionId));
    }
}

