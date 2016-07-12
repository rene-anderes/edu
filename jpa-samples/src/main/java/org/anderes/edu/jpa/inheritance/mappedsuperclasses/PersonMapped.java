package org.anderes.edu.jpa.inheritance.mappedsuperclasses;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersonMapped {

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
