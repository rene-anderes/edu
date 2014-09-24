package org.anderes.edu.employee.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Repräsentiert eine E-Mail Adresse
 */
@Entity(name="EMAIL")
public class EmailAddress {

	@Id
    @Column(name = "EMAIL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(name = "EMAIL_ADDRESS")
    private String address;

    public EmailAddress() {        
    }
    
    public EmailAddress(String address) {
        setAddress(address);
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }
}
