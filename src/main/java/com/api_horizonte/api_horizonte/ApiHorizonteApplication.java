package com.api_horizonte.api_horizonte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiHorizonteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiHorizonteApplication.class, args);
	}

}
