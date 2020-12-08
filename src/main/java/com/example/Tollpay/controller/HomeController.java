package com.example.Tollpay.controller;

import com.example.Tollpay.dto.Amount;
import com.example.Tollpay.log.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String welcome(){

        Log.print("GET REQUEST FOR HOME CONTROLLER");
        return "Connected to Server";

    }
    @RequestMapping(value = "/addAmount",method = RequestMethod.POST)
    public ResponseEntity<?> addAmount(@RequestBody Amount amount){
        System.out.println("amount recieved === "+ amount);
        return new ResponseEntity<>("Amount Added", HttpStatus.OK);
    }
}
