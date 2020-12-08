package com.example.Tollpay.service;

import com.example.Tollpay.dto.LoginResponse;
import com.example.Tollpay.dto.Session;
import com.example.Tollpay.dto.User;

public interface TravellerService {
    public void registerTraveller(User user);
    public boolean authenticateUserData(String email,String password);
    public LoginResponse loginUser(String email);

    void checkTollPlazaInRange(Double latitude, Double longitude, Integer token);
}
