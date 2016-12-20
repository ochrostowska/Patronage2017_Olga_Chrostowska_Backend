package com.patronage.ochrostowska.zadanie1.services;
import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.models.Movie;

import java.util.Set;


public interface MovieService {

	Set<Movie> findAll();
	
	Movie findById(int id);

	Movie findByName(String name);

	Actor addActor(Movie m, Actor a);

	Movie save(Movie m);
	
	Movie update(int id, Movie m);
	
	int deleteById(int id);
	
	void deleteAll();
	
	boolean isExist(Movie m);
	
}
