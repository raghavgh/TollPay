package com.example.Tollpay.service;

import com.example.Tollpay.TollpayApplication;
import com.example.Tollpay.dto.LoginData;
import com.example.Tollpay.dto.LoginResponse;
import com.example.Tollpay.dto.TRAVELLER;
import com.example.Tollpay.log.Log;
import com.example.Tollpay.repository.TravellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class TravellerServiceImpl implements TravellerService {


    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                    Math.cos(Math.toRadians(lat1)) *
                            Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

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
    public boolean authenticateUserData(String email,String password) {
        Log.print("SERVICE CLASS -> FUNCTION -> authenticateUserData");

        TRAVELLER traveller = travellerRepository.findByEmail(email);

        if(traveller == null) {
            Log.print("traveller null");
            return false;
        }
        if(!traveller.getPassword().equals(password)){
            Log.print("Traveller data == > " + traveller.toString());
            throw new RuntimeException("Password mismatch.");
        }
        return true;
    }
    private Integer generateToken(){
        return TollpayApplication.queue.remove();
    }

    @Override
    public LoginResponse loginUser(String email){
        Integer token = generateToken();
        TollpayApplication.tokenHash[token] = true;
        return new LoginResponse(token,email);
    }

    @Override
    public void checkTollPlazaInRange(Double latitude, Double latitude1) {

    }


}
