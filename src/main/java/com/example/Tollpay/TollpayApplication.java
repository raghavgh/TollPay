package com.example.Tollpay;

import com.example.Tollpay.dto.User;
import com.example.Tollpay.entity.Traveller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class TollpayApplication {
	public static boolean tokenHash[];
	public static Integer tokenNumber = 0;
	public static Queue<Integer> queue;
	public static Map<Integer, User> credentialHash;

	public static void main(String[] args) {
		tokenHash = new boolean[10000];
		credentialHash = new HashMap<>();
		queue = new LinkedList<>();
		for(int i = 0; i < 10000;i++){
			queue.add(i);
		}
		SpringApplication.run(TollpayApplication.class, args);
	}

}
