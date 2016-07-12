package org.anderes.edu.jpa.manytomany;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Beispiel welches die Möglichkeiten der Basic Attributes veranschaulicht.
 */
@Entity
public class Employee {

	public enum Gender { MALE, FEMALE };
	
	@Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;
    
	@Column(name = "F_NAME", nullable = false, length = 200)
    private String firstname;
	
	@Column(name = "L_NAME", nullable = false, length = 200)
    private String lastname;

	@Column(precision=8, scale=2)
	private BigDecimal salary;
	
	@Temporal(TemporalType.DATE)
	private Calendar birthday;
	
    @Enumerated(EnumType.STRING)
    private Gender gender;
	
    @ManyToMany(cascade = { CascadeType.ALL } )
    @JoinTable(name = "EMP_PROJ",
        joinColumns = { @JoinColumn(name = "EMP_ID", referencedColumnName = "ID") },
        inverseJoinColumns = { @JoinColumn(name = "PROJ_ID", referencedColumnName = "ID") })
	private Set<Project> projects = new HashSet<Project>();
    
    Employee() {
        super();
    }

    public Employee(final String firstname, final String lastname) {
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

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
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

    public void addProject(Project project) {
        projects.add(project);
    }
}
