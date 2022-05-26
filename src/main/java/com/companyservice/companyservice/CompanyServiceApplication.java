package com.companyservice.companyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.Authorization.authorizationservice.configuration.AppConfig;

@Import(AppConfig.class)
@SpringBootApplication
@ComponentScan({"com.companyservice.companyservice","com.Authorization.authorizationservice.configuration"})
public class CompanyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyServiceApplication.class, args);
		System.out.println("\n Company Service.!");
	}

}
