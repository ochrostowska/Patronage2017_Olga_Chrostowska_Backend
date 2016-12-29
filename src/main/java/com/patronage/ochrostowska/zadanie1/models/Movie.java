package com.patronage.ochrostowska.zadanie1.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.HashSet;

import java.util.concurrent.atomic.AtomicInteger;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {

    private static final AtomicInteger COUNT = new AtomicInteger(0);
    public static final Movie GHOST = new Movie();

    @Id
    private int id;
    private String title;
    private String genre;
    private String year;
    private String director;
    private HashSet<Actor> actors = new HashSet<>();

    public HashSet<Actor> getActors() {
        return actors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAutoId() { this.id = COUNT.incrementAndGet();}
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Movie(String title, String year, String genre, String director) {
        this.id = COUNT.incrementAndGet();
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.director = director;
    }

    public Movie(String title, String year, String genre, String director, HashSet<Actor> actors) {
        this.id = COUNT.incrementAndGet();
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.director = director;
        this.actors = actors;
    }

    Movie() {}
}
