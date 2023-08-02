package com.example.thebra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class ThebraApplication {
	public static void main(String[] args) {
		SpringApplication.run(ThebraApplication.class, args);
	}

}
