package com.example.Tollpay.dto;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TRAVELLER {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String mobileNum;

}
