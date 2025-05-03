package com.example.api_act_orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiActOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiActOrdersApplication.class, args);
	}

}
