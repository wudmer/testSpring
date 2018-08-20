package com.abaza.test.dao.impl;

import com.abaza.test.dao.AuthorDao;
import com.abaza.test.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AuthorDaoImpl implements AuthorDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Author author) {

        getSession().save(author);
    }

    @Override
    public void update(Author author) {
        getSession().update(author);
    }

    @Override
    public List getAll() {
        return getSession().createCriteria(Author.class).list();
    }

    @Override
    public Author getById(String id) {
        return getSession().get(Author.class, Long.valueOf(id));
    }

    @Override
    public void deleteByID(String id) {
        Author author = getById(id);
        getSession().delete(author);
    }

    @Override
    public List getOlderThan(String years) {
        Query query = getSession().getNamedQuery("getAuthorsWhichOlder").setString("olderDate", years);
        return  query.list();
    }

    @Override
    public List getAuthorsWhoHaveMoreBooksThan(String number) {
        Query query = getSession().getNamedQuery("getAuthorsWhoHaveMoreBooksThan").setString("number", number);
        return query.list();

    }

    @Override
    public Author getAuthorWhoHasMostBooks() {
        Object singleResult = getSession().getNamedQuery("getAuthorWhoHasMostBooks").setMaxResults(1).getSingleResult();
        return (Author)singleResult;
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
