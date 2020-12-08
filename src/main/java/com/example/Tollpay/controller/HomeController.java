package com.example.Tollpay.controller;

import com.example.Tollpay.TollpayApplication;
import com.example.Tollpay.controller.checker.LoginChecker;
import com.example.Tollpay.dto.Amount;
import com.example.Tollpay.dto.Profile;
import com.example.Tollpay.dto.Session;
import com.example.Tollpay.dto.User;
import com.example.Tollpay.log.Log;
import com.example.Tollpay.service.TravellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    TravellerService service;
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseEntity<?> getTest(){
        return new ResponseEntity<>("Hy from server",HttpStatus.OK);
    }

    @GetMapping
    public String welcome(){
        Log.print("GET REQUEST FOR HOME CONTROLLER");
        return "Connected to Server";

    }
    @RequestMapping(value = "/addAmount",method = RequestMethod.POST)
    public ResponseEntity<?> addAmount(@RequestBody Amount amount){
        if(LoginChecker.isUserLogined(amount.getToken())) {
            System.out.println("amount recieved === " + amount);
            boolean status = service.addAmount(amount);
            String res = "";
            if (status == true) {
                res = "Amount added Successfully";
            } else {
                res = "Amount addition failed";
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>("Please login before ",HttpStatus.OK);
    }

    @RequestMapping(value = "/profile",method = RequestMethod.POST)
    public ResponseEntity<?> getProfile(@RequestBody Session session){
        User user = TollpayApplication.credentialHash.get(session.getToken());
        Profile profile = service.addDataIntoProfile(user);
        return new ResponseEntity<>(profile,HttpStatus.OK);
    }
}
