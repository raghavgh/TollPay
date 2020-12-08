package com.example.Tollpay.repository;

import com.example.Tollpay.entity.TollCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TollChargesRepository  extends CrudRepository<TollCharges,Integer> {
}
