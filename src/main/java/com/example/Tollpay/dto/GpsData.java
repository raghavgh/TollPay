package com.example.Tollpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GpsData {
    private Double latitude;
    private Double longitude;
    private String email;
    private Integer token;
}
