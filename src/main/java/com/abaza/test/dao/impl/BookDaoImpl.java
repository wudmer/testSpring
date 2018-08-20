package com.abaza.test.dao.impl;

import com.abaza.test.dao.BookDao;
import com.abaza.test.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Book book) {
       getSession().save(book);
    }

    @Override
    public void update(Book book) {
        getSession().update(book);
    }

    @Override
    public List getAll() {
        return getSession().createCriteria(Book.class).list();
    }

    @Override
    public Book getById(String id) {
        return getSession().get(Book.class, id);
    }

    @Override
    public void deleteById(String id) {
        getSession().delete(getById(id  ));
    }

    private Session getSession() {
       return sessionFactory.getCurrentSession();
    }

}
