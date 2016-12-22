package com.patronage.ochrostowska;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.services.ActorServiceImpl;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ActorControllerTest {

    String name = "Some";
    String surname = "Beauty";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private ActorServiceImpl service;

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
        mockMvc.perform(get("/actors/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void actorFound() throws Exception {
        Actor a = new Actor("Found", "Actor");
        int savedId = service.save(a).getId();
        mockMvc.perform(get("/actors/" + savedId))
                .andExpect(status().isOk());
    }


    @Test
    public void add() throws Exception {
        Actor a = new Actor(name, surname);
        mockMvc.perform(MockMvcRequestBuilders.post("/actors")
                .contentType(contentType)
                .content(mapper.writeValueAsString(a)))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
    }


    @Test
    public void update() throws Exception {
        Actor a = new Actor(name, surname);
        int savedId = service.save(a).getId();
        Actor updatedA = new Actor("Ola", "Fasola");
        mockMvc.perform(MockMvcRequestBuilders.put("/actors/" + savedId)
                .contentType(contentType)
                .content(mapper.writeValueAsString(updatedA)))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
        Actor newA = service.findById(savedId);
        assertEquals(updatedA.getName(), newA.getName());
        assertEquals(updatedA.getSurname(), newA.getSurname());
    }


    @Test
    public void delete() throws Exception {
        Actor a = new Actor(name, surname);
        Actor saved = service.save(a);
        mockMvc.perform(MockMvcRequestBuilders.delete("/actors/" + saved.getId()))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk());
        mockMvc.perform(get("/actors/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/actors"))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(get("/actors"))
                .andExpect(content().contentType(contentType)).andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("[]", content);
    }

    @Test
    public void findById() throws Exception {
        Actor actor = new Actor(name, surname);
        Actor saved = service.save(actor);
        mockMvc.perform(get("/actors/" + saved.getId()))
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(saved.getId())))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/actors"))
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void notFound() throws Exception {
        mockMvc.perform(get("/actors/666"))
                .andExpect(status().isNotFound());
    }
}
