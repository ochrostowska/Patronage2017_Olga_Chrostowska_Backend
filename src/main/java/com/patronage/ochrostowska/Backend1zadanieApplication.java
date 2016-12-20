package com.patronage.ochrostowska;

import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.services.ActorServiceImpl;
import com.patronage.ochrostowska.zadanie1.models.Movie;
import com.patronage.ochrostowska.zadanie1.services.MovieServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Backend1zadanieApplication {

	public static void main(String[] args) {
		SpringApplication.run(Backend1zadanieApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ActorServiceImpl service, MovieServiceImpl movieService) {
		return(evt) -> {
		};
	}
}
