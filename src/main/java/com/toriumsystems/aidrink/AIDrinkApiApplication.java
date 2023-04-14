package com.toriumsystems.aidrink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AIDrinkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AIDrinkApiApplication.class, args);
	}

}
