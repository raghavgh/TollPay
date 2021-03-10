package com.example.Tollpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Profile {
    private String name;
    private String email;
    private String mobileNum;
    private String vehicleNumber;
    private String vehicleType;
    private Double walletAmount;
}
