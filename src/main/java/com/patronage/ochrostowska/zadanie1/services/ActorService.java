package com.patronage.ochrostowska.zadanie1.services;

import com.patronage.ochrostowska.zadanie1.models.Actor;

import java.util.Set;

public interface ActorService {

    Set<Actor> findAll();

    Actor findById(int id);

    Actor save(Actor a);

    Set<Actor> saveMany(Set<Actor> list);

    Actor update(int id, Actor a);

    int deleteById(int id);

    void deleteAll();

    int isExist(Actor a);

}
