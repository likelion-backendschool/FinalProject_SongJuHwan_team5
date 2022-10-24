package com.ll.finalProject.week1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week1Application {

	public static void main(String[] args) {
		SpringApplication.run(Week1Application.class, args);
	}

}
