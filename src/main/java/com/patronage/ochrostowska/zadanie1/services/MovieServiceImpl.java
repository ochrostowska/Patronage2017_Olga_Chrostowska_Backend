package com.patronage.ochrostowska.zadanie1.services;

import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.models.Movie;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MovieServiceImpl implements MovieService {

    private Set<Movie> movies;

    public MovieServiceImpl() {
        movies = new HashSet<>();
    }

    public MovieServiceImpl(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public Set<Movie> findAll() {
        return movies;
    }

    @Override
    public Movie findById(int id) {
        for (Movie m : movies) {
            if (m.getId() == id) {
                return m;
            }
        }
        return Movie.GHOST;
    }

    @Override
    public Movie findByName(String name) {
        for (Movie m : movies) {
            if (m.getTitle().equals(name)) {
                return m;
            }
        }
        return Movie.GHOST;
    }

    @Override
    public Actor addActor(Movie m, Actor a) {
        for (Actor actor : m.getActors()) {
            if (actor.getName().equals(a.getName()) && actor.getSurname().equals(a.getSurname())) {
                return Actor.GHOST;
            }
        }
        m.getActors().add(a);
        return a;
    }

    @Override
    public Movie save(Movie m) {
        if (m.getTitle().trim().isEmpty() || m.getYear().trim().isEmpty() || m.getDirector().trim().isEmpty() || m.getGenre().trim().isEmpty()) {
            return Movie.GHOST;
        }
        if (isExist(m)) {
            return Movie.GHOST;
        }
        movies.add(m);
        return m;
    }

    @Override
    public Movie update(int id, Movie m) {
        if (m.getTitle().trim().isEmpty() || m.getYear().trim().isEmpty() || m.getDirector().trim().isEmpty() || m.getGenre().trim().isEmpty()) {
            return Movie.GHOST;
        }
        for (Movie a : movies)
            if (a.getId() == id) {
                m.setId(a.getId());
                movies.remove(a);
                movies.add(m);
                return m;
            }
        return Movie.GHOST;
    }

    @Override
    public int deleteById(int id) {
        for (Movie a : movies) {
            if (a.getId() == id) {
                movies.remove(a);
                return id;
            }
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        movies.clear();
    }

    @Override
    public boolean isExist(Movie r) {
        for (Movie m : movies) {
            if (m.getTitle().equals(r.getTitle()) && m.getYear().equals(r.getYear()) && m.getDirector().equals(r.getDirector())) {
                return true;
            }
        }
        return false;
    }
}

