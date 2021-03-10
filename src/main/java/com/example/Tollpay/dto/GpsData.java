package com.example.Tollpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GpsData {
    private Coordinates coordinates;
    private Integer token;
}
