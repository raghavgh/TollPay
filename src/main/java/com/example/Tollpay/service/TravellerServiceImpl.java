package com.example.Tollpay.service;

import com.example.Tollpay.TollpayApplication;
import com.example.Tollpay.dto.LoginResponse;
import com.example.Tollpay.dto.User;
import com.example.Tollpay.entity.TollCharges;
import com.example.Tollpay.entity.TollPlaza;
import com.example.Tollpay.entity.Traveller;
import com.example.Tollpay.entity.Vehicle;
import com.example.Tollpay.log.Log;
import com.example.Tollpay.repository.TollChargesRepository;
import com.example.Tollpay.repository.TollPlazaRepository;
import com.example.Tollpay.repository.TravellerRepository;
import com.example.Tollpay.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class TravellerServiceImpl implements TravellerService {
    List<TollPlaza> tolls;
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

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    TollChargesRepository tollChargesRepository;

    @Autowired
    TollPlazaRepository tollPlazaRepository;

    @Override
    public void registerTraveller(User user) {
        //Log for debugging
        Log.print("SERVICE CLASS -> FUNCTION -> registerTraveller");
        //add user to the database
        Traveller traveller = new Traveller(user.getEmail()
                ,user.getPassword(),user.getName(),user.getPassword(),0.0);
        travellerRepository.save(traveller);
//        Vehicle vehicle = new Vehicle(user.getVehicleNumber(),user.getVehicleType(),traveller.getId());
//        vehicleRepository.save(vehicle);

    }

    @Override
    public boolean authenticateUserData(String email,String password) {
        Log.print("SERVICE CLASS -> FUNCTION -> authenticateUserData");

        Traveller user = travellerRepository.findByEmail(email);

        if(user == null) {
            Log.print("traveller null");
            return false;
        }
        if(!user.getPassword().equals(password)){
            Log.print("Traveller data == > " + user.toString());
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
        //get Email from database
        Traveller traveller = travellerRepository.findByEmail(email);
        Vehicle vehicle =vehicleRepository.findByUserId(traveller.getId());
        User user = new User(traveller.getId(),traveller.getName(),traveller.getPassword()
                ,traveller.getEmail(),traveller.getMobileNum(),vehicle.getVehicleNumber(),vehicle.getVehicleType());
        // use hash to store user information with token
        TollpayApplication.credentialHash.put(token,user);
        return new LoginResponse(token,user.getEmail());
    }
    private boolean deductAmount(Integer token,Long tollId){
        User user = TollpayApplication.credentialHash.get(token);

        //Logic to get toll charges for type
        TollCharges charges = tollChargesRepository.getTollChargesByTollIdAndType(tollId,user.getVehicleType());
        Double tollCharges = charges.getCharges();

        //Logic to get current balance of use
        Double currentBalance = travellerRepository.findAmountByEmail(user.getEmail()).get(0);

        if(currentBalance >= tollCharges){
            currentBalance -= tollCharges;
            travellerRepository.updateByEmail(currentBalance,user.getEmail());
        }
        else{
            return false;
        }
        //Logic to update balance of user
        return true;
    }

    private void loadTollData(){
        tolls = tollPlazaRepository.findAll();
    }

    private void openGates(){

    }
    private void payByManualMethod(){

    }

    //remaining code
    @Override
    public void checkTollPlazaInRange(Double latitude, Double longitude, Integer token) {
        if(tolls.size() == 0){
            loadTollData();
        }
        for(TollPlaza toll : tolls){
            if(distance(toll.getLatitude(),toll.getLatitude(),latitude,longitude,"K")*1000 <= 20){
                if(deductAmount(token,toll.getTollId()))
                    openGates();
                else{
                    payByManualMethod();
                }
            }
        }
    }


}
