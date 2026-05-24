package com.step.hotel_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HotelAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelAppApplication.class, args);
	}

}
