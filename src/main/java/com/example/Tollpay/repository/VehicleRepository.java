package com.example.Tollpay.repository;

import com.example.Tollpay.entity.TollCharges;
import com.example.Tollpay.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle,String> {
}
