package com.ulrichcoding.hrmi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HumanResourceManagementInsuranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanResourceManagementInsuranceApplication.class, args);
	}

}
