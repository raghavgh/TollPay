package com.example.Tollpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RangeStatus {
    private String responseType;
    private boolean rangeStatus;
    private Double tollCharges;
    private Double currentBalance;
}
