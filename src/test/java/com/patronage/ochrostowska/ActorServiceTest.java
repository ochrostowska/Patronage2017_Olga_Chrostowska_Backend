package com.patronage.ochrostowska;

import com.patronage.ochrostowska.zadanie1.models.Actor;
import com.patronage.ochrostowska.zadanie1.services.ActorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.support.NullValue;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ActorServiceTest {

    @Autowired
    ActorServiceImpl service;

    @Test
    public void add() throws Exception {
        Actor a = new Actor("Some", "Actress");
        Actor saved = service.save(a);
        assertEquals(a.getSurname(), saved.getSurname());
        assertEquals(a.getName(), saved.getName());
    }

    @Test
    public void update() throws Exception {
        Actor a = new Actor("Some", "Actor");
        Actor saved = service.save(a);
        int savedId = saved.getId();
        Actor newA = new Actor("Ola", "Super");
        Actor updated = service.update(savedId, newA);
        assertEquals(savedId, updated.getId());
        Actor gottenById = service.findById(savedId);
        assertEquals(gottenById.getName(), updated.getName());
        assertEquals(gottenById.getSurname(), updated.getSurname());
    }

    @Test
    public void updateFail() throws Exception {
        Actor a = new Actor("Fail", "Actor");
        Actor saved = service.save(a);
        int savedId = saved.getId();
        Actor newA = new Actor("Ola", null);
        Actor updated = service.update(savedId, newA);
        assertEquals(null, updated);
    }

    @Test
    public void delete() throws Exception {
        Actor a = new Actor("Delete", "Actor");
        Actor saved = service.save(a);
        int savedId = saved.getId();
        int deletedId = service.deleteById(savedId);
        assertEquals(savedId, deletedId);
    }

    @Test
    public void deleteAll() throws Exception {
        service.deleteAll();
        assertEquals(0, service.findAll().size());
    }

    @Test
    public void findById() throws Exception {
        Actor a = new Actor("Find", "Actor");
        Actor saved = service.save(a);
        int savedId = saved.getId();
        int foundId = service.findById(savedId).getId();

        assertEquals(savedId, foundId);
    }

    @Test
    public void findAll() throws Exception {
        int size = service.findAll().size();
        Actor a = new Actor("All", "Actor");
        Actor saved = service.save(a);
        Set<Actor> actors = service.findAll();

        assertEquals(size+1, actors.size());
        assertTrue(actors.contains(saved));
    }

    @Test
    public void notFound() throws Exception {
        Actor notFound = service.findById(666);
        assertEquals(null, notFound);
    }
}
