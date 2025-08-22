package com.example.storemanagementbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.example.storemanagementbackend")
@EntityScan(basePackages = {"com.example.storemanagementbackend.entity", "com.example.storemanagementbackend.model"})
@EnableScheduling
public class StoremanagementbackendApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(StoremanagementbackendApplication.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
	}

}
