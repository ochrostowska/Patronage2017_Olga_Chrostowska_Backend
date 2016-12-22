package com.patronage.ochrostowska;

import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.models.Movie;
import com.patronage.ochrostowska.zadanie1.services.ActorServiceImpl;
import com.patronage.ochrostowska.zadanie1.services.MovieServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MovieServiceTest {

    String name = "Some";
    String surname = "Beauty";
    String title = "Barambuczek";
    String genre = "Comedy";
    String year = "2016";
    String director = "Olga";

    @Autowired
    MovieServiceImpl movieService;
    @Autowired
    ActorServiceImpl actorService;


    @Test
    public void add() throws Exception {
        Movie m = new Movie("Add","2020", genre, director);
        Movie saved = movieService.save(m);
        assertEquals(m.getTitle(), saved.getTitle());
        assertEquals(m.getDirector(), saved.getDirector());
    }

    @Test
    public void update() throws Exception {
        Movie m = new Movie("Hipcio","2000", genre, director);
        Movie saved = movieService.save(m);
        int savedId = saved.getId();
        Movie newM = new Movie("Update","2016","Comedy", "Olga");
        Movie updated = movieService.update(savedId, newM);
        assertEquals(savedId, updated.getId());
        Movie gottenById = movieService.findById(savedId);
        assertEquals(gottenById.getTitle(), updated.getTitle());
        assertEquals(gottenById.getDirector(), updated.getDirector());
    }

    @Test
    public void updateFail() throws Exception {
        Movie m = new Movie("Fail",year,genre, director);
        Movie saved = movieService.save(m);
        int savedId = saved.getId();
        Movie newM = new Movie(null,"2016","Comedy", "Olga");
        Movie updated = movieService.update(savedId, newM);
        assertEquals(null, updated);
    }

    @Test
    public void delete() throws Exception {
        Movie m = new Movie("Delete",year,genre, director);
        Movie saved = movieService.save(m);
        int savedId = saved.getId();
        int deletedId = movieService.deleteById(savedId);
        assertEquals(savedId, deletedId);
    }

    @Test
    public void deleteAll() throws Exception {
        movieService.deleteAll();
        assertEquals(0, movieService.findAll().size());
    }

    @Test
    public void findById() throws Exception {
        Movie m = new Movie("Find",year,genre, director);
        Movie saved = movieService.save(m);
        int savedId = saved.getId();
        int foundId = movieService.findById(savedId).getId();

        assertEquals(savedId, foundId);
    }

    @Test
    public void findAll() throws Exception {
        int size = movieService.findAll().size();
        Movie m = new Movie("Find All",year,genre, director);
        Movie saved = movieService.save(m);
        Set<Movie> movies = movieService.findAll();

        assertEquals(size+1, movies.size());
        assertTrue(movies.contains(saved));
    }

    @Test
    public void notFound() throws Exception {
        Movie notFound = movieService.findById(666);
        assertEquals(null, notFound);
    }
}
