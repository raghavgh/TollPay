package com.example.Tollpay.service;

import com.example.Tollpay.TollpayApplication;
import com.example.Tollpay.dto.*;
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
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TravellerServiceImpl implements TravellerService {
    List<TollPlaza> tolls = new ArrayList<>();
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
        Traveller traveller = new Traveller(user.getName(), user.getPassword(), user.getEmail(),
                user.getMobileNum(), 0.0);
        System.out.println(user);
        travellerRepository.save(traveller);
        Vehicle vehicle = new Vehicle(user.getVehicleNumber(),user.getVehicleType(),traveller.getId());
        System.out.println(vehicle);
        vehicleRepository.save(vehicle);

    }

    private Traveller findByEmail(String email){
        Iterable<Traveller> travellers = travellerRepository.findAll();
        for(Traveller traveller : travellers){
            System.out.println(traveller);
            if(traveller.getEmail().equals(email)){
                return traveller;
            }
        }
        return null;
    }

    @Override
    public boolean authenticateUserData(String email,String password) {
        Log.print("SERVICE CLASS -> FUNCTION -> authenticateUserData");

        Traveller user = findByEmail(email);

        if(user == null) {
            Log.print("traveller null");
            return false;
        }
        if(!user.getPassword().equals(password)){
            Log.print("Traveller data == > " + user.toString());
            return false;
        }
        return true;
    }
    private Integer generateToken(){
        return TollpayApplication.queue.remove();
    }

    private Vehicle findByUserId(Integer id){
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        for(Vehicle vehicle: vehicles){

            if(vehicle.getId().equals(id)){
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public LoginResponse loginUser(String email,String password){
        Integer token = generateToken();
        TollpayApplication.tokenHash[token] = true;
        //get Email from database
        try {
            Traveller traveller = findByEmail(email);
            if(traveller.getPassword().equals(password)) {
                Vehicle vehicle = findByUserId(traveller.getId());
                User user = new User(traveller.getId(), traveller.getName(), traveller.getPassword()
                        , traveller.getEmail(), traveller.getMobileNum(), vehicle.getVehicleNumber(), vehicle.getVehicleType());
                // use hash to store user information with token
                TollpayApplication.credentialHash.put(token, user);
                return new LoginResponse(token, user.getEmail());
            }
            else{
                return new LoginResponse(-1,"");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new LoginResponse(-1,"");
        }
    }

    @Override
    public Profile addDataIntoProfile(User user) {
        Double currentAmount = getCurrentAmount(user.getEmail());
        Profile profile = new Profile(user.getName(),user.getEmail(),user.getMobileNum(),
                user.getVehicleNumber(), user.getVehicleType(),currentAmount);
        return profile;
    }

    private Double getTollCharge(Integer tollId){
        Double tollCharge = 0.0;
        Iterable<TollCharges> charges = tollChargesRepository.findAll();
        for(TollCharges charge : charges){
            if(charge.getTollId().equals(tollId)){
                tollCharge = charge.getCharges();
            }
        }
        return tollCharge;
    }

    private Double getCurrentAmount(String email){
        Double currentBalance = 0.0;
        Iterable<Traveller> travellers = travellerRepository.findAll();
        for(Traveller traveller : travellers){
            if(traveller.getEmail().equals(email)){
                currentBalance = traveller.getWalletAmount();
                break;
            }
        }
        return currentBalance;
    }

    private boolean deductAmount(Integer token,Integer tollId){
        User user = TollpayApplication.credentialHash.get(token);

        //Logic to get toll charges for type
        Double tollCharge = getTollCharge(tollId);

        //Logic to get current balance of use
        Double currentBalance = getCurrentAmount(user.getEmail());

        if(currentBalance >= tollCharge){
            currentBalance -= tollCharge;
            travellerRepository.updateByEmail(currentBalance,user.getEmail());
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public PaymentResponse getPaymentResponse(Integer token, Integer tollId) {
        boolean status  = deductAmount(token,tollId);
        if(status){
            return new PaymentResponse("Payment",true,getCurrentAmount(
                    TollpayApplication.credentialHash.get(token).getEmail()), getTollCharge(tollId));
        }
        else{
            return new PaymentResponse("Payment",false,
                    -1.0,-1.0);
        }
    }

    @Override
    public RangeStatus getRangeStatus(Integer token, Integer tollId) {
        String email = TollpayApplication.credentialHash.get(token).getEmail();
        return new RangeStatus("Range",true,getTollCharge(tollId),getCurrentAmount(email));
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
    public Integer checkTollPlazaInRange(Double latitude, Double longitude, Integer token) {
        if(tolls.size() == 0){
            loadTollData();
        }
        Integer res = -1;
        for(TollPlaza toll : tolls){
            if(distance(toll.getLatitude(),toll.getLatitude(),latitude,longitude,"K")*1000 <= 20){
                res = toll.getTollId();
//                if(deductAmount(token,toll.getTollId())) {
//                    openGates();
//                    break;
//                }
//                else{
//                    payByManualMethod();
//                    break;
//                }
                break;
            }
        }
        return res;
    }

    public boolean updateAmountByEmail(String email , Double addAmount) {
        try{
            Iterable<Traveller> travellers = travellerRepository.findAll();
            for (Traveller traveller : travellers) {
                if (traveller.getEmail().equals(email)) {
                    traveller.setWalletAmount(addAmount);
                }
            }
            travellerRepository.saveAll(travellers);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean addAmount(Amount amount) {
        try {
            User user = TollpayApplication.credentialHash.get(amount.getToken());
            Double currentAmount = getCurrentAmount(user.getEmail());
            currentAmount += amount.getAmount();
            updateAmountByEmail(user.getEmail(), currentAmount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
