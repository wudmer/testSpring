package com.abaza.test.dao.impl;

import com.abaza.test.dao.AuthorBookDao;
import com.abaza.test.model.AuthorBook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AuthorBookDaoImpl implements AuthorBookDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(AuthorBook authorBook) {
        getSession().save(authorBook);
    }

    @Override
    public void update(AuthorBook authorBook) {
        getSession().update(authorBook);
    }

    @Override
    public List getAll() {
        return getSession().createCriteria(AuthorBook.class).list();
    }

    @Override
    public AuthorBook getById(String id) {
        return getSession().get(AuthorBook.class, Long.valueOf(id));
    }

    @Override
    public void deleteById(String id) {
        AuthorBook authorBook = getById(id);
        getSession().delete(authorBook);
    }




    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }



}
