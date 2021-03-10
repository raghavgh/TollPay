package com.example.Tollpay.entity;

import com.example.Tollpay.dto.Coordinates;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TollPlaza")
@Data
public class TollPlaza {
    @Id
    @Column(name = "TOLL_ID")
    private Integer tollId;
    @Column(name = "TOLL_NAME")
    private String TollName;
    private Double latitude;
    private Double longitude;
}
