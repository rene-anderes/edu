package org.anderes.edu.jpa.inheritance.mappedsuperclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "PERSON_NR")
    private Integer personNr;
    
    public Integer getPersonNr() {
		return personNr;
	}

	public void setPersonNr(Integer personNr) {
		this.personNr = personNr;
	}

	public Long getId() {
        return id;
    }
}
