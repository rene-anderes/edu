package org.anderes.edu.jpa.inheritance.mappedsuperclasses;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NP")
public class NaturalPerson extends Person {
    
    private String firstname;
    private String lastname;
    
    NaturalPerson() {
        super();
    }

    public NaturalPerson(final String firstname, final String lastname) {
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
}
