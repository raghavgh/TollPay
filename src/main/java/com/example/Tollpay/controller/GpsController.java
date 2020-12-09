package com.example.Tollpay.controller;

import com.example.Tollpay.controller.checker.LoginChecker;
import com.example.Tollpay.dto.GpsData;
import com.example.Tollpay.dto.PaymentResponse;
import com.example.Tollpay.dto.RangeStatus;
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
    @RequestMapping(value = "/auto/coordinates",method = RequestMethod.POST)
    public ResponseEntity<?> getFinalStatus(@RequestBody GpsData gpsData){
        ResponseEntity<?> responseEntity;
        if(LoginChecker.isUserLogined(gpsData.getToken())){
            Integer tollId = -1;
            tollId = service.checkTollPlazaInRange(gpsData.getCoordinates().getLatitude()
                    ,gpsData.getCoordinates().getLongitude(),gpsData.getToken());
            PaymentResponse paymentResponse;
            if(tollId != -1){
                paymentResponse = service.getPaymentResponse(gpsData.getToken(),
                        tollId);
                responseEntity = new ResponseEntity<>(paymentResponse,HttpStatus.OK);
            }
            else{
                RangeStatus status = new RangeStatus("Range",false,-1.0,-1.0);
                responseEntity = new ResponseEntity<>(status,HttpStatus.OK);
            }
        } else{
            responseEntity = new ResponseEntity<>("Login first",HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/pre/coordinates",method = RequestMethod.POST)
    public ResponseEntity<?> getRangeStatus(@RequestBody GpsData gpsData){
        ResponseEntity<?> responseEntity;
        if(LoginChecker.isUserLogined(gpsData.getToken())){
            Integer tollId = -1;
            tollId = service.checkTollPlazaInRange(gpsData.getCoordinates().getLatitude()
                    ,gpsData.getCoordinates().getLongitude(),gpsData.getToken());
            if(tollId != -1){
                RangeStatus status = service.getRangeStatus(gpsData.getToken(),
                        tollId,gpsData.getCoordinates());
                responseEntity = new ResponseEntity<>(status,HttpStatus.OK);
            }
            else{
                RangeStatus status = new RangeStatus("Range",false,-1.0,-1.0);
                responseEntity = new ResponseEntity<>(status,HttpStatus.OK);
            }
        }
        else{
            responseEntity = new ResponseEntity<>("Login first",HttpStatus.OK);
        }
        return responseEntity;
    }
}
