package org.anderes.edu.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Beispiel welches die Möglichkeiten der Basic Attributes veranschaulicht.
 * Es werden die grundlegenden Annotations von JPA eingesetzt.
 */
@Entity
@Table(name = "T_PERSON")
public class Person {

	public enum Gender { MALE, FEMALE };
	
	@Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;
    
	@Basic
	@Column(name = "F_NAME", nullable = false, length = 200)
    private String firstname;
	
	// The @Basic is not required in general because it is the default.
	@Column(name = "L_NAME", nullable = false, length = 200)
    private String lastname;

	@Column(precision=8, scale=2)
	private BigDecimal salary;
	
	private LocalDate birthday;
	
    @Enumerated(EnumType.STRING)
    private Gender gender;
	
	@Transient
	private String state;
    
    Person() {
        super();
    }

    public Person(final String firstname, final String lastname) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
