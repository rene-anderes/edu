package org.anderes.edu.relations.onetomany;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    
    private String firstname;
    private String lastname;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Company company;
    
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

	public void setCompany(final Company company) {
		this.company = company;
	}
	
	public Company getCompany() {
		return this.company;
	}
}
