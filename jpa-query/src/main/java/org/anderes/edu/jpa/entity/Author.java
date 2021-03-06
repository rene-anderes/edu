package org.anderes.edu.jpa.entity;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(
      name = "Author.All.OrderByLastname", 
      query = "SELECT a FROM Author a ORDER BY a.lastName ASC"),
    @NamedQuery(
      name = "Author.ByLastname", 
      query = "SELECT a FROM Author a WHERE a.lastName = :lastname")
   })

public class Author {

    @Id @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String vita;

    @ManyToMany
    @JoinTable(name = "AUTHOR_BOOK", 
        joinColumns = { @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID") }
    )
    private Collection<Book> books = new HashSet<>();

    Author() {
    }

    public Author(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVita() {
        return vita;
    }

    public void setVita(String vita) {
        this.vita = vita;
    }

    public Long getId() {
        return id;
    }
}
