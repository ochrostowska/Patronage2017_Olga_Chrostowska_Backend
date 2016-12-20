package com.patronage.ochrostowska.zadanie1.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.HashSet;

import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Movie {

    @Id
    private int id;
    private String title;
    private String genre;
    private String year;
    private HashSet<Actor> actors = new HashSet<>();

    private static final AtomicInteger count = new AtomicInteger(0);

    public HashSet<Actor> getActors() {
        return actors;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Movie(String title, String year, String genre) {
        this.id = count.incrementAndGet();
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public Movie(String title, String year, String genre, HashSet<Actor> actors) {
        this.id = count.incrementAndGet();
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.actors = actors;
    }

    Movie() {}
}
