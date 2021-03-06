package com.patronage.ochrostowska.zadanie1.controllers;

import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.models.Movie;
import com.patronage.ochrostowska.zadanie1.services.ActorServiceImpl;
import com.patronage.ochrostowska.zadanie1.services.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/movies")
public class MovieRestController {

    @Autowired
    private MovieServiceImpl service;
    @Autowired
    private ActorServiceImpl actorService;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity getById(@PathVariable("id") int id) {
        if (service.findById(id) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity addOne(@RequestBody Movie movie) {
        if(movie.getTitle().trim().isEmpty() || movie.getGenre().trim().isEmpty() || movie.getYear().trim().isEmpty() || movie.getDirector().trim().isEmpty())
            return new ResponseEntity<>("There are some empty fields, movie wasn't added", HttpStatus.BAD_REQUEST);
        if(service.isExist(movie)) return new ResponseEntity("Movie already exists" , HttpStatus.CONFLICT);
        movie.setAutoId();
        service.save(movie);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/actors", method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity addActor(@PathVariable int id, @RequestBody Actor actor) {
        Movie m = service.findById(id);
        if (m == null)
            return new ResponseEntity<>("No movie found for ID " + id, HttpStatus.NOT_FOUND);
        if(actor.getName()==null || actor.getSurname()==null)
            return new ResponseEntity<>("There are some empty fields, actor wasn't added", HttpStatus.BAD_REQUEST);
        int actorId = actorService.isExist(actor);
        if (actorId != 0) {
            service.addActor(m, actorService.findById(actorId));
            return new ResponseEntity<>("Added actor with id " + actorId, HttpStatus.OK);
        } else {
            Actor a = actorService.save(actor);
            service.addActor(m, a);
        }
        return new ResponseEntity<>("Added new actor " + actor, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/actors", method = RequestMethod.GET)
    ResponseEntity getActors(@PathVariable int id) {
        Movie m = service.findById(id);
        if (m == null)
            return new ResponseEntity<>("No movie found for ID " + id, HttpStatus.NOT_FOUND);
        if (m.getActors() == null)
            return new ResponseEntity<>("There's no cast for movie with id " + id, HttpStatus.NOT_ACCEPTABLE);
        else {
            return new ResponseEntity<>(m.getActors(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity deleteOne(@PathVariable int id) {
        if (service.deleteById(id) == 0) {
            return new ResponseEntity<>("No movie found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAll() {
        service.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    ResponseEntity updateOne(@PathVariable int id, @RequestBody Movie movie) {
        movie = service.update(id, movie);

        if (movie == null) {
            return new ResponseEntity<>("No movie found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
