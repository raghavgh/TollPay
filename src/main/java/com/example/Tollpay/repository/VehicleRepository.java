package com.example.Tollpay.repository;

import com.example.Tollpay.entity.TollCharges;
import com.example.Tollpay.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle,String> {
}
