package com.example.Tollpay.entity;

import com.ibm.db2.cmx.annotation.Id;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TOLL_CHARGES")
public class TollCharges {
    @Id
    private Long tollId;
    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;
    @Column(name = "TOLL_CHARGES")
    private Double charges;
}
