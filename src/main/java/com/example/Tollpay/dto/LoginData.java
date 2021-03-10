package com.example.Tollpay.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class LoginData {
    private String email;
    private String password;
    private Integer token;
}
