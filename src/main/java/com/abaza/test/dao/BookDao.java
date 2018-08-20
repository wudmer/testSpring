package com.abaza.test.dao;

import com.abaza.test.model.Book;

import java.util.List;

public interface BookDao {


    void save(Book book);

    void update(Book book);

    List getAll();

    Book getById(String id);

    void deleteById(String id);
}
