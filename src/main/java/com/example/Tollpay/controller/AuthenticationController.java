package com.example.Tollpay.controller;

import com.example.Tollpay.log.Log;
import com.example.Tollpay.dto.TRAVELLER;
import com.example.Tollpay.dto.Welcome;
import com.example.Tollpay.service.TravellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    TravellerService travellerService;

    //when user come to register this api hits
    @PostMapping(value = "welcome")
    public Welcome registerNewUser(@RequestBody TRAVELLER traveller){
        try{
            //Logs for debugging
            Log.print("GET REQUEST 'welcome' FOR AUTHENTICATION CONTROLLER");
            Log.print(traveller.toString());
            Log.print(""+ traveller.getId());

            //send user data to service class to process
            travellerService.registerTraveller(traveller);
            return new Welcome("Welcome to TollPay " + traveller.getName());
        }
        catch (Exception e){
            return new Welcome("Sorry........");
        }
    }

    //get mapping to test authentication api
    @GetMapping("test")
    public String test(){
        return "Working";
    }

}