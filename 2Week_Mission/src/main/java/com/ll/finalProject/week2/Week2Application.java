package com.ll.finalProject.week2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class Week2Application {

	public static void main(String[] args) {
		SpringApplication.run(Week2Application.class, args);
	}

}
