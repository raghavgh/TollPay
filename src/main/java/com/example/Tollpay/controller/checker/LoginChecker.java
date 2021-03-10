package com.example.Tollpay.controller.checker;

import com.example.Tollpay.TollpayApplication;

public class LoginChecker {
    public static boolean isUserLogined(Integer token){
        if(token != null && TollpayApplication.tokenHash[token]){
            return true;
        }
        return false;
    }
}
