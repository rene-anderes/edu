package org.anderes.edu.jpa.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NP")
@DiscriminatorValue(value = "NP")
public class NaturalPerson extends Person {

    private String lastname;

    @Column(name = "LASTNAME")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
