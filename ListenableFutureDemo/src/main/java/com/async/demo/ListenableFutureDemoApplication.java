package com.async.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ListenableFutureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListenableFutureDemoApplication.class, args);
	}
}
