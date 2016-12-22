package com.patronage.ochrostowska;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MovieControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

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
    }

    @Test
    public void actorNotFound() throws Exception {
        mockMvc.perform(get("/movies/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void actorFound() throws Exception {
        Movie m = new Movie("Found", "2016", "Comedy", "Olga");
        int id = movieService.save(m).getId();
        mockMvc.perform(get("/movies/" +id))
                .andExpect(status().isOk());
    }


    @Test
    public void add() throws Exception {
        Movie m = new Movie("New", "2016", "Comedy", "Olga");
        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                .contentType(contentType)
                .content(mapper.writeValueAsString(m)))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }


    @Test
    public void update() throws Exception {
        Movie m = new Movie("Some Movie", "2016", "Comedy", "Olga");
        int savedId = movieService.save(m).getId();
        Movie updated = new Movie("Updated", "2016", "Comedy", "Olga");
        mockMvc.perform(MockMvcRequestBuilders.put("/movies/" + savedId)
                .contentType(contentType)
                .content(mapper.writeValueAsString(updated)))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
        Movie newM = movieService.findById(savedId);
        assertEquals(updated.getTitle(), newM.getTitle());
        assertEquals(updated.getDirector(), newM.getDirector());
    }


    @Test
    public void delete() throws Exception {
        Movie m = new Movie("Delete", "2016", "Comedy", "Olga");
        Movie saved = movieService.save(m);
        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/" + saved.getId()))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
        mockMvc.perform(get("/movies/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/movies"))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(get("/movies"))
                .andExpect(content().contentType(contentType)).andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("[]", content);
    }

    @Test
    public void findById() throws Exception {
        Movie m = new Movie("FindById", "2016", "Comedy", "Olga");
        Movie saved = movieService.save(m);
        mockMvc.perform(get("/movies/" + saved.getId()))
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(saved.getId())))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void notFound() throws Exception {
        mockMvc.perform(get("/movies/666"))
                .andExpect(status().isNotFound());
    }
}
