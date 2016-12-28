package com.patronage.ochrostowska.zadanie1.services;


import com.patronage.ochrostowska.zadanie1.models.Actor;
import org.springframework.stereotype.Component;


import java.util.HashSet;

import java.util.Set;


@Component
public class ActorServiceImpl implements ActorService {

    private Set<Actor> actors;

    public ActorServiceImpl() {
        actors = new HashSet<>();
    }

    @Override
    public Set<Actor> findAll() {
        return actors;
    }

    @Override
    public Actor findById(int id) {
        for (Actor a : actors) if (a.getId() == id) return a;
        return null;
    }

    @Override
    public Actor save(Actor a) {
        if (a.getName() == null || a.getSurname() == null) return null;
        if(isExist(a)!=0) return null;
        a.setAutoId();
        actors.add(a);
        return a;
    }

    @Override
    public Set<Actor> saveMany(Set<Actor> list) {
        Set<Actor> added = new HashSet<>();
        for (Actor a : list) {
            if (a.getName() != null && a.getSurname() != null) {
                added.add(a);
                actors.add(a);
            }
        }
        return added;
    }

    @Override
    public Actor update(int id, Actor a) {
        if(a.getName()==null || a.getSurname()==null) return null;
        for (Actor actor : actors)
            if (actor.getId() == id) {
                a.setId(actor.getId());
                actors.remove(actor);
                actors.add(a);
                return a;
            }
        return null;
    }

    @Override
    public int deleteById(int id) {
        for (Actor a : actors)
            if (a.getId() == id) {
                actors.remove(a);
                return id;
            }
        return 0;
    }

    @Override
    public void deleteAll() {
        actors.clear();
    }

    @Override
    public int isExist(Actor a) {
        for (Actor actor : actors)
            if (actor.getName().equals(a.getName()) && actor.getSurname().equals(a.getSurname())) return actor.getId();
        return 0;
    }


}
