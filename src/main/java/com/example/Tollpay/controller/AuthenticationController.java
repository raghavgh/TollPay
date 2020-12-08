package com.example.Tollpay.controller;

import com.example.Tollpay.TollpayApplication;
import com.example.Tollpay.dto.LoginData;
import com.example.Tollpay.dto.Session;
import com.example.Tollpay.log.Log;
import com.example.Tollpay.dto.User;
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
    public ResponseEntity<?> registerNewUser(@RequestBody User user){
        try{
            //Logs for debugging
            Log.print("GET REQUEST 'welcome' FOR AUTHENTICATION CONTROLLER");
            Log.print(user.toString());
            Log.print(""+ user.getId());

            //send user data to service class to process
            if(!travellerService.authenticateUserData(
                    user.getEmail(), user.getPassword())) {
                travellerService.registerTraveller(user);
                return ResponseEntity.status(HttpStatus.OK).body("Welcome " + user.getEmail());
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body("You are already registered " + user.getEmail());
        }
        catch (Exception e){
            e.printStackTrace();
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
                return new ResponseEntity<>(travellerService.loginUser(loginData.getEmail(),loginData.getPassword()),HttpStatus.OK);
            }
            else{
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User is not Registered or Invalid Password");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("EXCEPTION OCCURRED  == "+ e);
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseEntity<?> logoutUser(@RequestBody Session session){
        TollpayApplication.credentialHash.remove(session.getToken());
        TollpayApplication.tokenHash[session.getToken()] = false;
        TollpayApplication.queue.add(session.getToken());
        return new ResponseEntity<>("Logout Successful",HttpStatus.OK);
    }




    //get mapping to test authentication api
    @GetMapping("test")
    public String test(){
        return "Test";
    }

}