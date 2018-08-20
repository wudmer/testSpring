package com.abaza.test.api;

import com.abaza.test.config.AppConfig;
import com.abaza.test.dao.BookDao;
import com.abaza.test.model.Book;
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
import java.util.Map;

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
public class BooksControllerTest {

    @Autowired
    private BookDao bookDao;
    private BooksController controller;

    @Before
    public void setUp() throws Exception {
        controller = new BooksController(bookDao);
    }

    @Test
    public void getAll() {
        ResponseEntity responseEntity = controller.getAll();
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(4, list.size());
    }

    @Test
    public void getBookById() {
        String id = "1";
        ResponseEntity responseEntity = controller.getBookById(id);
        Book book = new Gson().fromJson(responseEntity.getBody().toString(), Book.class);
        assertEquals("ss", book.getName());
    }

    @Test
    public void addBook() {
        String id = "5";
        controller.addBook(id, "asd", "2000-05-12", "tech", "5");
        ResponseEntity responseEntity = controller.getBookById(id);
        assertNotEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Book book = new Gson().fromJson(responseEntity.getBody().toString(), Book.class);
        assertEquals("asd", book.getName());

    }

    @Test
    public void editBook() {
        String id = "2";
        controller.editBook(id, "asd", "1907-10-05", "tech", "5");
        ResponseEntity responseEntity = controller.getBookById(id);
        assertNotEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Book book = new Gson().fromJson(responseEntity.getBody().toString(), Book.class);
        assertEquals("asd", book.getName());
    }

    @Test
    public void booksByGenre() {
        ResponseEntity responseEntity = controller.booksByGenre();
        Map<String, Double> map = new Gson().fromJson(responseEntity.getBody().toString(), Map.class);
        assertEquals(2.0, map.get("tech"), 0.0001);
    }

    @Test
    public void delete() {
        controller.delete("0");
        ResponseEntity responseEntity = controller.getAll();
        List list = new Gson().fromJson(responseEntity.getBody().toString(), List.class);
        assertEquals(3, list.size());
    }
}