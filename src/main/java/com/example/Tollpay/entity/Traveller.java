package com.example.Tollpay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TRAVELLER")
@Data
@AllArgsConstructor
public class Traveller {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "MOBILE_NUM")
    private String mobileNum;
    @Column(name = "WALLET_AMOUNT")
    private Double walletAmount;

    public Traveller(String name,String password,String email,String mobileNum,Double walletAmount){
        this.email = email;
        this.password = password;
        this.mobileNum = mobileNum;
        this.name = name;
        this.walletAmount = walletAmount;
    }
    public Traveller(){

    }
}
