package com.example.Tollpay.repository;

import com.example.Tollpay.dto.TRAVELLER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//This CrudRepository Class Provided by JPA which provide us all function to perform on databases
//with our entity data
//for more clarity check class defination
public interface TravellerRepository extends CrudRepository<TRAVELLER,Integer> {
}
