package com.example.Tollpay.Controller;

import com.example.Tollpay.Log.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @PostMapping("welcome")
    public String registerNewUser(){
        Log.print("Welcome ");
        return "Welcome to the TollPay ";
    }
    @GetMapping("test")
    public String test(){
        return "Working";
    }

}