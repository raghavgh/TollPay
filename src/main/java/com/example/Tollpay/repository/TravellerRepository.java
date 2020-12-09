package com.example.Tollpay.repository;

import com.example.Tollpay.dto.User;
import com.example.Tollpay.entity.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

//This CrudRepository Class Provided by JPA which provide us all function to perform on databases
//with our entity data
//for more clarity check class defination
public interface TravellerRepository extends CrudRepository<Traveller,Integer> {
    @Transactional
    @Modifying
    @Query("update TRAVELLER  set WALLET_AMOUNT=?1 where EMAIL= ?2")
    public void updateByEmail(Double amount,String email);
}
