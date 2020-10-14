package com.example.Tollpay.controller;

import com.example.Tollpay.log.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String welcome(){

        Log.print("GET REQUEST FOR HOME CONTROLLER");
        return "Connected to Server";

    }
}
