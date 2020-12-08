package com.example.Tollpay.repository;

import com.example.Tollpay.entity.TollCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TollChargesRepository  extends CrudRepository<TollCharges,Integer> {
    @Query("SELECT TOLL_CHARGES" +
            "  FROM JDT75816.TOLL_CHARGES where TOLL_ID =?1 and VEHICLE_TYPE=?2")
    public TollCharges getTollChargesByTollIdAndType(Long id,String vehicleType);
}
