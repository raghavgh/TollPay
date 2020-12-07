package com.example.Tollpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@SpringBootApplication
public class TollpayApplication {
	public static boolean tokenHash[];
	public static Integer tokenNumber = 0;
	public static Queue<Integer> queue;

	public static void main(String[] args) {
		tokenHash = new boolean[1000];
		queue = new LinkedList<>();
		for(int i = 0; i < 1000;i++){
			queue.add(i);
		}
		SpringApplication.run(TollpayApplication.class, args);
	}

}
