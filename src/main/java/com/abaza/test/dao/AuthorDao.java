package com.abaza.test.dao;

import com.abaza.test.model.Author;

import java.util.List;

public interface AuthorDao {


    void save(Author author);

    void update(Author author);

    List getAll();

    Author getById(String id);

    void deleteByID(String id);

    List getOlderThan(String years);

    List getAuthorsWhoHaveMoreBooksThan(String number);

    Author getAuthorWhoHasMostBooks();

}
