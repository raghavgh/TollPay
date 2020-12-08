package com.example.Tollpay.repository;

import com.example.Tollpay.dto.User;
import com.example.Tollpay.entity.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//This CrudRepository Class Provided by JPA which provide us all function to perform on databases
//with our entity data
//for more clarity check class defination
public interface TravellerRepository extends JpaRepository<Traveller,Integer> {
    public Traveller findByEmail(String email);
    @Query(value = "update TRAVELLER  set WALLET_AMOUNT=?1 where EMAIL= ?2")
    public void updateByEmail(Double amount,String email);
}
