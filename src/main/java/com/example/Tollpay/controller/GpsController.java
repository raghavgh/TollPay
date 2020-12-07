package com.example.Tollpay.controller;

import com.example.Tollpay.TollpayApplication;
import com.example.Tollpay.controller.checker.LoginChecker;
import com.example.Tollpay.dto.GpsData;
import com.example.Tollpay.service.TravellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gps")
public class GpsController {
    @Autowired
    TravellerService service;
    @RequestMapping(value = "/coord",method = RequestMethod.POST)
    public ResponseEntity<?> getCoordinates(@RequestBody GpsData gpsData){
        if(LoginChecker.isUserLogined(gpsData.getToken())){
            service.checkTollPlazaInRange(gpsData.getLatitude(),gpsData.getLatitude());
        }
        else{
            return new ResponseEntity<>("Please login", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("hy",HttpStatus.OK);
    }
}
