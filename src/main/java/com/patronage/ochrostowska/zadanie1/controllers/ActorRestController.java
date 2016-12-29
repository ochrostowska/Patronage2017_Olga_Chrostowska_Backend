package com.patronage.ochrostowska.zadanie1.controllers;


import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.models.Movie;
import com.patronage.ochrostowska.zadanie1.services.ActorServiceImpl;
import com.patronage.ochrostowska.zadanie1.services.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/actors")
public class ActorRestController {

    @Autowired
    private ActorServiceImpl service;
    @Autowired
    private MovieServiceImpl movieService;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity getById(@PathVariable int id) {
        if(service.findById(id)==Actor.GHOST) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity addOne(@RequestBody Actor actor) {
        if(actor.getName().trim().isEmpty() || actor.getSurname().trim().isEmpty())
            return new ResponseEntity<>("There are some empty fields, actor wasn't added", HttpStatus.BAD_REQUEST);
        if(service.isExist(actor)!=0) return new ResponseEntity("Actor already exists" , HttpStatus.CONFLICT);
        service.save(actor);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAll() {
        service.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity deleteOne(@PathVariable int id) {
        Actor a = service.findById(id);
        HashSet<Actor> cast;
        Actor toDelete = null;
        if (a == Actor.GHOST) return new ResponseEntity<>("No actor found for ID " + id, HttpStatus.NOT_FOUND);
        for (Movie m : movieService.findAll()) {
            cast = m.getActors();
            for (Actor actor : cast) {
                if (actor.getId() == a.getId()) {
                    toDelete = actor;
                    break;
                }
            }
            cast.remove(toDelete);
            toDelete = null;
        }
        service.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    ResponseEntity updateOne(@PathVariable int id, @RequestBody Actor actor) {
        if(actor.getName().trim().isEmpty() || actor.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Couldn't update actor with null values " + id, HttpStatus.BAD_REQUEST);
        }
        actor = service.update(id, actor);
        if(actor==Actor.GHOST) {
            return new ResponseEntity<>("No actor found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
