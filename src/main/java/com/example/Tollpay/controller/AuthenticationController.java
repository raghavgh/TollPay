package com.example.Tollpay.controller;

import com.example.Tollpay.TollpayApplication;
import com.example.Tollpay.dto.LoginData;
import com.example.Tollpay.log.Log;
import com.example.Tollpay.dto.TRAVELLER;
import com.example.Tollpay.service.TravellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    TravellerService travellerService ;





    //when user come to register this api hits
    @RequestMapping(value = "/welcome",method = RequestMethod.POST,headers = "Accept=application/json")
    public ResponseEntity<String> registerNewUser(@RequestBody TRAVELLER traveller){
        try{
            //Logs for debugging
            Log.print("GET REQUEST 'welcome' FOR AUTHENTICATION CONTROLLER");
            Log.print(traveller.toString());
            Log.print(""+ traveller.getId());

            //send user data to service class to process
            if(!travellerService.authenticateUserData(
                    traveller.getEmail(),traveller.getPassword())) {
                travellerService.registerTraveller(traveller);
                return ResponseEntity.status(HttpStatus.OK).body("Welcome " + traveller.getEmail());
            }
            return ResponseEntity.status(HttpStatus.OK).body("You are already registered " + traveller.getEmail());

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request is not correct");
        }
    }

    //when user logins
    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginData loginData){

        Log.print("POST REQUEST 'login' FOR AUTHENTICTION CONTROLLER");
        Log.print("DATA GOT FROM FRONTEND = " + loginData.toString());


        try {
            if(travellerService.authenticateUserData(loginData.getEmail(),loginData.getPassword())) {
                return new ResponseEntity<>(travellerService.loginUser(loginData.getEmail()),HttpStatus.OK);
            }
            else{
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User is not Registered");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("EXCEPTION OCCURED  == "+ e);
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseEntity<?> logoutUser(@RequestBody LoginData loginData){
        TollpayApplication.queue.add(loginData.getToken());
        TollpayApplication.tokenHash[loginData.getToken()] = false;
        return new ResponseEntity<>("Logout Successful",HttpStatus.OK);
    }




    //get mapping to test authentication api
    @GetMapping("test")
    public String test(){
        return "Test";
    }

}