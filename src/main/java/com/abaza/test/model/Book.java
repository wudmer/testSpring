package com.abaza.test.model;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    private String id;
    private String name;
    private String published;
    private String genre;
    private String rating;

    public Book() {
    }

    public Book(String id, String name, String published, String genre, String rating) {
        this.id = id;
        this.name = name;
        this.published = published;
        this.genre = genre;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
