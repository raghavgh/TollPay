package com.example.Tollpay.controller;

import com.example.Tollpay.log.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;

    @Test
    public void reachableTest(){
        assertEquals("Test",authenticationController.test());
        Log.print("Authentication Controller is Reachable");
    }
}