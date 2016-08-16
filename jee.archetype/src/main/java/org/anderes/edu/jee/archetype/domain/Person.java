package org.anderes.edu.jee.archetype.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Override
	public int hashCode() {
		 return new HashCodeBuilder(17, 37).append(firstname).append(lastname).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Person p = (Person) obj;
		return new EqualsBuilder().append(firstname, p.firstname).append(lastname, p.lastname).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("firstname", firstname).append("lastname", lastname).toString();
	}

}
