package com.example.Tollpay.Controller;

import com.example.Tollpay.Log.Log;
import com.example.Tollpay.dto.Welcome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
