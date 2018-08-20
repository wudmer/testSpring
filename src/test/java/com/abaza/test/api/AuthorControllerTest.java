package com.abaza.test.api;

import com.abaza.test.config.AppConfig;
import com.abaza.test.dao.AuthorDao;
import com.abaza.test.model.Author;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
@DbUnitConfiguration(databaseConnection = "dataSourceTest")
@DatabaseSetup("classpath:sampleData.xml")
public class AuthorControllerTest {

    @Autowired
    private AuthorDao authorDao;
    private AuthorController controller;

    @Before
    public void setUp() throws Exception {
        controller = new AuthorController(authorDao);
    }


    @Test
    public void getAll() {
        ResponseEntity responseEntity = controller.getAll();
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(list.size(), 3);
    }

    @Test
    public void getAuthorById() {
        String id = "1";
        ResponseEntity responseEntity = controller.getAuthorById(id);
        String json = responseEntity.getBody().toString();
        System.out.println(json);
        Author author = new Gson().fromJson(json, Author.class);
        assertEquals("Mr1", author.getName());
    }

    @Test
    public void getAuthorByIdNoFound() {
        String id = "5";
        ResponseEntity responseEntity = controller.getAuthorById(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void addAuthor() {
        controller.addAuthor("Abaza", "male", "1997-10-05");
        String id = "3";
        ResponseEntity responseEntity = controller.getAuthorById(id);
        assertNotEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Author author = new Gson().fromJson(responseEntity.getBody().toString(), Author.class);
        assertEquals("Abaza", author.getName());
    }

    @Test
    public void editAuthor() {
        String id = "0";
        controller.editAuthor(id, "abaza", "male", "1997-10-05");
        ResponseEntity responseEntity = controller.getAuthorById(id);
        assertNotEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Author author = new Gson().fromJson(responseEntity.getBody().toString(), Author.class);
        assertEquals("abaza", author.getName());
    }

    @Test
    public void deleteAuthor() {
        controller.deleteAuthor("0");
        ResponseEntity responseEntity = controller.getAll();
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(list.size(), 2);

    }

    @Test
    public void getOlderThan() {
        ResponseEntity responseEntity = controller.getOlderThan("50");
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(list.size(), 1);
    }

    @Test
    public void bookMoreThan() {
        ResponseEntity responseEntity = controller.bookMoreThan("3");
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(list.size(), 1);
    }

    @Test
    public void mostBooks() {
        ResponseEntity responseEntity = controller.mostBooks();
        Author author = new Gson().fromJson(responseEntity.getBody().toString(), Author.class);
        assertEquals("AA", author.getName());
    }
}