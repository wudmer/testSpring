package com.abaza.test.dao;

import com.abaza.test.model.AuthorBook;

import java.util.List;

public interface AuthorBookDao {

    void save(AuthorBook author);

    void update(AuthorBook author);

    List getAll();

    AuthorBook getById(String id);

    void deleteById(String id);
}
