package com.example.Tollpay.service;

import com.example.Tollpay.dto.LoginData;
import com.example.Tollpay.dto.TRAVELLER;
import org.springframework.web.bind.annotation.RequestBody;

public interface TravellerService {
    public void registerTraveller(TRAVELLER traveller);
    public boolean authenticateUserData(@RequestBody LoginData loginData);
}
