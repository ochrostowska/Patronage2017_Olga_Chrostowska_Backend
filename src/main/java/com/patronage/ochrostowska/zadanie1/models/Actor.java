package com.patronage.ochrostowska.zadanie1.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Actor {

    @Id
    private int id;
    private String name;
    private String surname;
    private static final AtomicInteger count = new AtomicInteger(0);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAutoId() {
        this.id = count.incrementAndGet();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Actor(String name, String surname) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Actor() {}

}
