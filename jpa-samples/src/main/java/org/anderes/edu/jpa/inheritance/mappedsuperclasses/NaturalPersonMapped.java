package org.anderes.edu.jpa.inheritance.mappedsuperclasses;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NP_MAPPED")
public class NaturalPersonMapped extends PersonMapped {
    
    private String firstname;
    private String lastname;
    
    NaturalPersonMapped() {
        super();
    }

    public NaturalPersonMapped(final String firstname, final String lastname) {
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
