package com.example.Tollpay.entity;


import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TOLL_CHARGES")
public class TollCharges {
    @Id
    private Integer id;
    private Integer tollId;
    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;
    @Column(name = "TOLL_CHARGES")
    private Double charges;
}
