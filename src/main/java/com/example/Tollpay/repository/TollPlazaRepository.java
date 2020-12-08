package com.example.Tollpay.repository;

import com.example.Tollpay.entity.TollPlaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TollPlazaRepository extends JpaRepository<TollPlaza,Long> {
}
