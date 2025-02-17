package com.example.sparta_3rd_newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class Sparta3rdNewsfeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sparta3rdNewsfeedApplication.class, args);
	}

}
