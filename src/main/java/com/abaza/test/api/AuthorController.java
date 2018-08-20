package com.abaza.test.api;

import com.abaza.test.dao.AuthorDao;
import com.abaza.test.model.Author;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorDao authorDao;

    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @RequestMapping
    public ResponseEntity getAll() {

        List list = authorDao.getAll();
        String json = new Gson().toJson(list);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getAuthorById(@PathVariable("id") String id) {
        Author author = authorDao.getById(id);
        if(author == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            String json = new Gson().toJson(author);
            return ResponseEntity.status(HttpStatus.OK).body(json);
        }

    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addAuthor(@RequestParam("name") String name,
                        @RequestParam("gender") String gender,
                        @RequestParam("born") String born) {
        Author author = new Author(name, gender, convertBirthDate(born));
        authorDao.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity editAuthor(@PathVariable("id") String id,
                                     @RequestParam("name") String name,
                                     @RequestParam("gender") String gender,
                                     @RequestParam("born") String born) {

        Author author = authorDao.getById(id);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            author.setName(name);
            author.setGender(gender);
            author.setBorn(convertBirthDate(born));
            authorDao.update(author);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAuthor(@PathVariable("id") String id) {
        authorDao.deleteByID(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @RequestMapping("/olderThan/{years}")
    public ResponseEntity getOlderThan(@PathVariable("years") String years) {
        List list = authorDao.getOlderThan(years);
        String json = new Gson().toJson(list);
        return ResponseEntity.status(HttpStatus.OK).body(json);

    }

    @RequestMapping(path = "/bookMoreThan/{number}")
    public ResponseEntity bookMoreThan(@PathVariable("number") String number) {
        List list = authorDao.getAuthorsWhoHaveMoreBooksThan(number);
        String json = new Gson().toJson(list);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @RequestMapping("/mostBooks")
    public ResponseEntity mostBooks(){
        Author hasMostBooks = authorDao.getAuthorWhoHasMostBooks();
        String json = new Gson().toJson(hasMostBooks);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


    private Date convertBirthDate(String born) {

        Date date = null;
        try {
            java.util.Date javaDate = new SimpleDateFormat("yyyy-MM-dd").parse(born);
            date = new Date(javaDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
