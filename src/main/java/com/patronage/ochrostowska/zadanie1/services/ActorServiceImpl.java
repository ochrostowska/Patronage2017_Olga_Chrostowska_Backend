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
        actors.add(new Actor(a.getName(), a.getSurname()));
        return a;
    }

    @Override
    public Set<Actor> saveMany(Set<Actor> list) {
        for (Actor a : list) {
            actors.add(a);
        }
        return list;
    }

    @Override
    public Actor update(int id, Actor a) {
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
        for (Actor a : actors) if (a.getId() == id) {
            actors.remove(a);
            return id;
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        for (Actor a : actors) actors.remove(a);
    }

    @Override
    public int isExist(Actor a) {
        for (Actor actor : actors)
            if (actor.getName() == a.getName() && actor.getSurname() == a.getSurname()) return actor.getId();
        return 0;
    }


}
