package com.abaza.test.api;

import com.abaza.test.dao.AuthorBookDao;
import com.abaza.test.model.AuthorBook;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author_book")
public class AuthorBookController {

    private AuthorBookDao authorBookDao;

    @Autowired
    public AuthorBookController(AuthorBookDao authorBookDao) {
        this.authorBookDao = authorBookDao;
    }

    @RequestMapping
    public ResponseEntity getAll() {
        List list = authorBookDao.getAll();
        String json = new Gson().toJson(list);
        return ResponseEntity.ok(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable("id") String id) {
        AuthorBook authorBook = authorBookDao.getById(id);
        if (authorBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            String json = new Gson().toJson(authorBook);
            return ResponseEntity.ok(json);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addAuthorBook(@RequestParam("bookId") String bookId,
                          @RequestParam("authorId") String authorId) {
        AuthorBook authorBook = new AuthorBook(bookId, Long.valueOf(authorId));
        authorBookDao.save(authorBook);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity editAutorBook(@PathVariable("id") String id,
                          @RequestParam("bookId") String bookId,
                          @RequestParam("authorId") String authorId) {
        AuthorBook authorBook = authorBookDao.getById(id);
        if (authorBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            authorBook.setAuthorId(Long.valueOf(authorId));
            authorBook.setBookId(bookId);
            authorBookDao.update(authorBook);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") String id) {
        authorBookDao.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
