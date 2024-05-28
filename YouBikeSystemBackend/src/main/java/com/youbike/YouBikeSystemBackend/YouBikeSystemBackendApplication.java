package com.youbike.YouBikeSystemBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class YouBikeSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouBikeSystemBackendApplication.class, args);
	}

}
