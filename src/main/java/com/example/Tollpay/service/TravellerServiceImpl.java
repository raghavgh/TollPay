package com.example.Tollpay.service;

import com.example.Tollpay.dto.LoginData;
import com.example.Tollpay.dto.TRAVELLER;
import com.example.Tollpay.log.Log;
import com.example.Tollpay.repository.TravellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class TravellerServiceImpl implements TravellerService {

    //it will find repository object and inject it into this class
    @Autowired
    TravellerRepository travellerRepository;

    @Override
    public void registerTraveller(TRAVELLER traveller) {
        //Log for debugging
        Log.print("SERVICE CLASS -> FUNCTION -> registerTraveller");

        //add user to the database
        travellerRepository.save(traveller);
    }

    @Override
    public boolean authenticateUserData(LoginData loginData) {
        Log.print("SERVICE CLASS -> FUNCTION -> authenticateUserData");

        TRAVELLER traveller = travellerRepository.findByEmail(loginData.getEmail());

        if(traveller == null) {
            Log.print("traveller null");
            return false;
        }
        if(!traveller.getPassword().equals(loginData.getPassword())){
            Log.print("Traveller data == > " + traveller.toString());
            throw new RuntimeException("Password mismatch.");
        }
        return true;
    }



}
