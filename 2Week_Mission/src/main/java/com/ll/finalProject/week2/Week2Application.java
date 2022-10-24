package com.ll.finalProject.week2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week2Application {

	public static void main(String[] args) {
		SpringApplication.run(Week2Application.class, args);
	}

}
