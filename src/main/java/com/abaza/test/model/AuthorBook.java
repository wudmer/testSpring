package com.abaza.test.model;

import javax.persistence.*;

@Entity
@Table(name = "author_book")
public class AuthorBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bookId;
    private long authorId;



    public AuthorBook(String bookId, long authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public AuthorBook() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
