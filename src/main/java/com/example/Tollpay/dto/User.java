package com.example.Tollpay.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String mobileNum;
    private String vehicleNumber;
    private String vehicleType;
}
