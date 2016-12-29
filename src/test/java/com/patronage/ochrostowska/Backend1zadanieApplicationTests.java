package com.patronage.ochrostowska;

import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.models.Movie;
import com.patronage.ochrostowska.zadanie1.services.ActorServiceImpl;

import com.patronage.ochrostowska.zadanie1.services.MovieServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import java.nio.charset.Charset;
import java.util.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class Backend1zadanieApplicationTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private ActorServiceImpl actorService;
    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        addDataForTesting(actorService, movieService);
    }

    public static void addDataForTesting(ActorServiceImpl actorService, MovieServiceImpl movieService) {
        Actor umaThurman = new Actor("Uma", "Thurman");
        Actor johnTravolta = new Actor("John", "Travolta");
        Actor bruceWillis = new Actor("Bruce", "Willis");
        Actor quentinTarantino = new Actor("Quentin", "Tarantino");
        Actor samuelLJackson = new Actor("Samuel L.", "Jackson");
        Actor timRoth = new Actor("Tim", "Roth");
        Actor lucyLiu = new Actor("Lucy", "Liu");
        Actor darylHannah = new Actor("Daryl", "Hannah");
        Actor michaelMadsen = new Actor("Michael", "Madsen");
        Actor davidCarradine = new Actor("David", "Carradine");
        Actor harveyKeitel = new Actor("Harvey", "Keitel");
        Actor steveBuscemi = new Actor("Steve", "Buscemi");
        Actor jamieFoxx = new Actor("Jamie", "Foxx");
        Actor christophWaltz = new Actor("Christoph", "Waltz");
        Actor leonardoDiCaprio = new Actor("Leonardo", "DiCaprio");
        Actor bradPitt = new Actor("Brad", "Pitt");

        Set<Actor> actors = new HashSet<>();
        actors.add(umaThurman);
        actors.add(johnTravolta);
        actors.add(bruceWillis);
        actors.add(quentinTarantino);
        actors.add(samuelLJackson);
        actors.add(timRoth);
        actors.add(lucyLiu);
        actors.add(darylHannah);
        actors.add(michaelMadsen);
        actors.add(davidCarradine);
        actors.add(harveyKeitel);
        actors.add(steveBuscemi);
        actors.add(jamieFoxx);
        actors.add(christophWaltz);
        actors.add(leonardoDiCaprio);
        actors.add(bradPitt);
        actorService.saveMany(actors);

        Movie pulpFiction = new Movie("Pulp Fiction", "1994",
                "Crime", "Quentin Tarantino");
        movieService.save(pulpFiction);
        movieService.addActor(pulpFiction, umaThurman);
        movieService.addActor(pulpFiction, johnTravolta);
        movieService.addActor(pulpFiction, bruceWillis);
        movieService.addActor(pulpFiction, quentinTarantino);

        Movie killBill = new Movie("Kill Bill", "2003",
                "Action", "Quentin Tarantino");
        movieService.save(killBill);
        movieService.addActor(killBill, umaThurman);
        movieService.addActor(killBill, lucyLiu);
        movieService.addActor(killBill, darylHannah);
        movieService.addActor(killBill, michaelMadsen);
        movieService.addActor(killBill, davidCarradine);

        Movie killBill2 = new Movie("Kill Bill 2", "2004",
                "Action", "Quentin Tarantino");
        movieService.save(killBill2);
        movieService.addActor(killBill2, umaThurman);
        movieService.addActor(killBill2, darylHannah);
        movieService.addActor(killBill2, michaelMadsen);
        movieService.addActor(killBill2, davidCarradine);

        Movie reservoirDogs = new Movie("Reservoir Dogs", "1992",
                "Crime", "Quentin Tarantino");
        movieService.save(reservoirDogs);
        movieService.addActor(reservoirDogs, quentinTarantino);
        movieService.addActor(reservoirDogs, harveyKeitel);
        movieService.addActor(reservoirDogs, michaelMadsen);
        movieService.addActor(reservoirDogs, timRoth);
        movieService.addActor(reservoirDogs, steveBuscemi);

        Movie django = new Movie("Django", "2012",
                "Crime", "Quentin Tarantino");
        movieService.save(django);
        movieService.addActor(django, quentinTarantino);
        movieService.addActor(django, jamieFoxx);
        movieService.addActor(django, christophWaltz);
        movieService.addActor(django, leonardoDiCaprio);
        movieService.addActor(django, samuelLJackson);

        Movie ingloriousBastards = new Movie("Inglorious Bastards", "2009",
                "Crime", "Quentin Tarantino");
        movieService.save(ingloriousBastards);
        movieService.addActor(ingloriousBastards, quentinTarantino);
        movieService.addActor(ingloriousBastards, christophWaltz);
        movieService.addActor(ingloriousBastards, bradPitt);
    }
}


