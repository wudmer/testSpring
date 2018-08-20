package com.abaza.test.api;

import com.abaza.test.dao.BookDao;
import com.abaza.test.model.Book;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;

    @Autowired
    public BooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @RequestMapping
    public ResponseEntity getAll() {
        List listBooks = bookDao.getAll();
        String json = new Gson().toJson(listBooks);
        return ResponseEntity.ok().body(json);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getBookById(@PathVariable("id") String id) {
        Book book = bookDao.getById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            String json = new Gson().toJson(book);
            return ResponseEntity.ok().body(json);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addBook(@RequestParam("id") String id,
                        @RequestParam("name") String name,
                        @RequestParam("published") String published,
                        @RequestParam("genre") String genre,
                        @RequestParam("rating") String rating) {

        Book book = new Book(id, name, published, genre, rating);
        bookDao.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity editBook(@PathVariable("id") String id,
                                           @RequestParam("name") String name,
                                           @RequestParam("published") String published,
                                           @RequestParam("genre") String genre,
                                           @RequestParam("rating") String rating) {

        Book book = bookDao.getById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            book.setName(name);
            book.setPublished(published);
            book.setGenre(genre);
            book.setRating(rating);
            bookDao.update(book);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @RequestMapping("/numberByGenre")
    public ResponseEntity booksByGenre(){
        Map<String, Long> collect = (Map<String, Long>) bookDao.getAll().stream().collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()));
        String json = new Gson().toJson(collect);
        return ResponseEntity.ok().body(json);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") String id) {
        bookDao.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
