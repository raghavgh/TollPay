package com.example.Tollpay.repository;

import com.example.Tollpay.entity.TollCharges;
import com.example.Tollpay.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle,String> {
    @Query("Select * from VEHICLE WHERE TRAVELLER_ID = ?1")
    public Vehicle findByUserId(Long id);
}
