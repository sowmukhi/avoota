package com.neoteric.avoota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvootaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvootaApplication.class, args);
	}

}
//http://localhost:8080/api/average-rating
//http://localhost:8080/api/average-rating-per-category
//http://localhost:8080/api/average-rating-and-subratings
//http://localhost:8080/api/rating-percentages
//http://localhost:8080/api/full-category-rating