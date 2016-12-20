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
        for (Movie m : movies) if (m.getId() == id) return m;
        return null;
    }

    @Override
    public Movie findByName(String name) {
        for (Movie m : movies) if (m.getTitle() == name) return m;
        return null;
    }

    @Override
    public Actor addActor(Movie m, Actor a) {
        for (Actor actor : m.getActors()) if(actor.getName() == a.getName() && actor.getSurname() == a.getSurname()) return null;
        m.getActors().add(a);
        return a;
    }

    @Override
    public Movie save(Movie m) {
        if(isExist(m)) return null;
        movies.add(new Movie(m.getTitle(), m.getYear(), m.getGenre(), m.getActors()));
        return m;
    }

    @Override
    public Movie update(int id, Movie m) {
        for (Movie a : movies)
            if (a.getId() == id) {
                m.setId(a.getId());
                movies.remove(a);
                movies.add(m);
                return m;
            }
        return null;
    }

    @Override
    public int deleteById(int id) {
        for (Movie a : movies)
            if (a.getId() == id) {
                movies.remove(a);
                return id;
            }
        return 0;
    }

    @Override
    public void deleteAll() {
        for (Movie a : movies) movies.remove(a);
    }

    @Override
    public boolean isExist(Movie r) {
        for (Movie m : movies)
            if (m.getTitle() == r.getTitle() && m.getYear() == r.getYear()) return true;
        return false;
    }
}

