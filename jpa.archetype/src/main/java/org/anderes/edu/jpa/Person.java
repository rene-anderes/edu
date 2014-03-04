package org.anderes.edu.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Long id;
    
    private String firstname;
    private String lastname;
    
    Person() {
        super();
    }

    public Person(final String firstname, final String lastname) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }
}
