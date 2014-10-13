package org.anderes.edu.employee.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(address).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        final EmailAddress rhs = (EmailAddress) obj;
        return new EqualsBuilder().append(address, rhs.address).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("address", address).toString();
    }
}
