package com.example.Tollpay.Controller;

import com.example.Tollpay.Log.Log;
import com.example.Tollpay.dto.RegistrationData;
import com.example.Tollpay.dto.Welcome;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @PostMapping(value = "welcome")
    public Welcome registerNewUser(@RequestBody RegistrationData registrationData){
        try{
            Log.print("Welcome ");
            return new Welcome("Welcome to TollPay " + registrationData.getName());
        }
        catch (Exception e){
            return new Welcome("Sorry for inconvinience");
        }
    }
    @GetMapping("test")
    public String test(){
        return "Working";
    }

}