package com.cybage.tms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import com.cybage.repo.UserRepo;

@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.cybage.repo")
@SpringBootApplication(scanBasePackages = "com.cybage")
@EntityScan(basePackages = "com.cybage.model")
public class TmsApplication {

	@Autowired
	private UserRepo repo;

	public static void main(String[] args) {
		SpringApplication.run(TmsApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
