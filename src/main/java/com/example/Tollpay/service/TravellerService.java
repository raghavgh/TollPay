package com.example.Tollpay.service;

import com.example.Tollpay.dto.*;

public interface TravellerService {
    public void registerTraveller(User user);
    public boolean authenticateUserData(String email,String password);
    public LoginResponse loginUser(String email,String password);
    public Profile getProfileData(User user);
    public Integer checkTollPlazaInRange(Double latitude, Double longitude, Integer token);
    public boolean addAmount(Amount amount);
    public PaymentResponse getPaymentResponse(Integer token, Integer tollId);
    public RangeStatus getRangeStatus(Integer token, Integer tollId);
}
