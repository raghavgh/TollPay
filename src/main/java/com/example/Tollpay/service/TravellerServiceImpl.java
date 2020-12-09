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
import org.w3c.dom.ranges.Range;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class TravellerServiceImpl implements TravellerService {
    private static List<TollPlaza> tolls = new ArrayList<>();
    private static Map<Integer ,Double> tollCharges = new HashMap<>();
    public static double distance(Double lat1,
                                  Double lat2, Double lon1,
                                  Double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return((c * r)*1000);
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
                Profile profile = getProfileData(user);
                // use hash to store user information with token
                TollpayApplication.credentialHash.put(token, profile);
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
    public Profile getProfileData(User user) {
        //getting data from db
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
            System.out.println(traveller);
            if(traveller.getEmail().equals(email)){
                currentBalance = traveller.getWalletAmount();
                break;
            }
        }
        return currentBalance;
    }


    private Double deductAmount(Integer token,Integer tollId){
        Profile profile = TollpayApplication.credentialHash.get(token);

        //Logic to get toll charges for type
        Double tollCharge = tollCharges.get(tollId);

        //Logic to get current balance of use
        Double currentBalance = TollpayApplication.credentialHash.get(token).getWalletAmount();

        if(currentBalance >= tollCharge){
            currentBalance -= tollCharge;

            //DB OPERation
            travellerRepository.updateByEmail(currentBalance,profile.getEmail());
            profile.setWalletAmount(currentBalance);
            TollpayApplication.credentialHash.replace(token,profile);
            return currentBalance;
        }
        else{
            return -1236.0;
        }
    }

    @Override
    public PaymentResponse getPaymentResponse(Integer token, Integer tollId) {
        Double deductedAmount  = deductAmount(token,tollId);
        if(deductedAmount >= 0){
            return new PaymentResponse("Payment",true
                    ,deductedAmount, tollCharges.get(tollId));
        }
        else{
            return new PaymentResponse("Payment",false,
                    -1.0,-1.0);
        }
    }

    @Override
    public RangeStatus getRangeStatus(Integer token, Integer tollId, Coordinates coordinates) {
        String email = TollpayApplication.credentialHash.get(token).getEmail();
        Double currentAmount = TollpayApplication.credentialHash.get(token).getWalletAmount();
        boolean status = false;
        if(checkTollPlazaInRange(coordinates.getLatitude(),coordinates.getLongitude(),token) > 0) {
            return new RangeStatus("Range", true
                    , tollCharges.get(tollId), currentAmount);
        }
        else{
            return new RangeStatus("Range",false,tollCharges.get(tollId),currentAmount);
        }
    }

    private void loadTollData(){
        tolls = tollPlazaRepository.findAll();
        for(TollPlaza toll : tolls){
            Double charges = getTollCharge(toll.getTollId());
            tollCharges.put(toll.getTollId(),charges);
        }
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
            Double dist = distance(toll.getLatitude(),latitude,toll.getLongitude(),longitude);
            System.out.println(dist);
            if(dist <= 20.0){
                res = toll.getTollId();
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
            if(amount.getAmount() <= 0 || amount.getAmount() >=200000)
                return false;
            Profile profile = TollpayApplication.credentialHash.get(amount.getToken());
            Double currentAmount = TollpayApplication.credentialHash.get(amount.getToken()).getWalletAmount();
            currentAmount += amount.getAmount();
            travellerRepository.updateByEmail( currentAmount, profile.getEmail());
            profile.setWalletAmount(currentAmount);
            TollpayApplication.credentialHash.replace(amount.getToken(),profile);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
