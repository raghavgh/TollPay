package com.example.Tollpay.entity;

import com.ibm.db2.cmx.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "VEHICLE")
@Data
@AllArgsConstructor
public class Vehicle {
    @Id
    @Column(name = "VEHICLE_NO")
    private String vehicleNumber;
    @Column(name = "TYPE")
    private String vehicleType;
    @Column(name = "TRAVELLER_ID")
    private Integer id;
}
