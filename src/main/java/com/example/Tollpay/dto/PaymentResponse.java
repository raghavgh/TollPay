package com.example.Tollpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String responseType;
    private boolean paymentStatus;
    private Double remainingBalance;
    private Double deductedTollCharges;
}
