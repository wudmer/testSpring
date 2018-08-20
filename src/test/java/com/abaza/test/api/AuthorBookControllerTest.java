package com.abaza.test.api;

import com.abaza.test.config.AppConfig;
import com.abaza.test.dao.AuthorBookDao;
import com.abaza.test.model.AuthorBook;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
@DbUnitConfiguration(databaseConnection = "dataSourceTest")
@DatabaseSetup("classpath:sampleData.xml")
public class AuthorBookControllerTest {


    @Autowired
    private AuthorBookDao authorBookDao;
    private AuthorBookController controller;

    @Before
    public void setUp() throws Exception {
        controller = new AuthorBookController(authorBookDao);
    }

    @Test
    public void getAll() {
        ResponseEntity responseEntity = controller.getAll();
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(4, list.size());
    }

    @Test
    public void getById() {
        String id = "1";
        ResponseEntity responseEntity = controller.getById(id);
        assertNotEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getByIdNotFound() {
        String id = "5";
        ResponseEntity responseEntity = controller.getById(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void addAuthorBook() {
        controller.addAuthorBook("2", "3");
        ResponseEntity responseEntity = controller.getAll();
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(5, list.size());
    }

    @Test
    public void editAuthorBook() {
        String id = "1";
        controller.editAutorBook(id, "1", "1");
        ResponseEntity responseEntity = controller.getById(id);
        assertNotEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        AuthorBook authorBook = new Gson().fromJson(responseEntity.getBody().toString(), AuthorBook.class);
        assertEquals(1L, authorBook.getAuthorId());
    }

    @Test
    public void delete() {
        controller.delete("0");
        ResponseEntity responseEntity = controller.getAll();
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(3, list.size());
    }
}