package com.OutReachCompany.OutReachCompanyDetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.Authorization.authorizationservice.configuration.AppConfig;
@Import(AppConfig.class)
@SpringBootApplication
@ComponentScan({"com.OutReachCompany.OutReachCompanyDetails","com.Authorization.authorizationservice.configuration"})
public class OutReachCompanyDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutReachCompanyDetailsApplication.class, args);
		System.out.println("OutReach Micro service 1 started.!");
	}

}
