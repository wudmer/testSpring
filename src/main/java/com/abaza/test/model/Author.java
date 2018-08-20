package com.abaza.test.model;

import javax.persistence.*;
import java.sql.Date;


@NamedQueries({
        @NamedQuery(
                name = "getAuthorsWhichOlder",
                query = "from Author a where (datediff(CURDATE(), a.born)/365) >= :olderDate order by a.born"
        ),
        @NamedQuery(
                name = "getAuthorsWhoHaveMoreBooksThan",
                query = "select a from Author a inner join AuthorBook ab on a.id = ab.authorId group by a.id having count(ab.id) >= :number"
        ),
        @NamedQuery(
                name = "getAuthorWhoHasMostBooks",
                query = "select a as c from Author a inner join AuthorBook ab on a.id = ab.authorId group by a.id order by count(a.id) desc"
        )
})
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private String gender;
    private Date born;

    public Author() {
    }

    public Author(String name, String gender, Date born) {
        this.name = name;
        this.gender = gender;
        this.born = born;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }
}
