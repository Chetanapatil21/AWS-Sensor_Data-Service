package com.aqms.sensordataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
//Class definition for SensordataServiceApplication
public class SensordataServiceApplication {
	// Main method to start the Spring application 
	public static void main(String[] args) {
		SpringApplication.run(SensordataServiceApplication.class, args);
	}

	// Bean definition for RestTemplate with LoadBalanced annotation
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
