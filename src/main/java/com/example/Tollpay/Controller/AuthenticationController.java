package com.example.Tollpay.Controller;

import com.example.Tollpay.dto.DashboardData;
import com.example.Tollpay.dto.RegestrationData;
import com.example.Tollpay.dto.Welcome;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {
    @PostMapping("/welcome")
    public Welcome registerNewUser(@RequestParam RegestrationData regestrationData){
        return new Welcome("Welcome to the TollPay "+ regestrationData.getName());

    }

}