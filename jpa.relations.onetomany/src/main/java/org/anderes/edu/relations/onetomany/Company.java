package org.anderes.edu.relations.onetomany;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;


@Entity
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy="company")
	private Collection<Person> employees = new HashSet<>();

	Company() {};
	
	public Company(final String name) {
		super();
		this.name = name;
	}

	public Collection<Person> getEmployees() {
		return Collections.unmodifiableCollection(employees);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* ------------- Pattern für JPA bidirektionale Beziehung ------------ */ 

	public void addEmployee(final Person person) {
	    person.setCompany(this);
	}

	/*package*/ void internalAddEmployee(Person person) {
	    employees.add(person);
	}
	
	public void removeEmployee(final Person person) {
	    if (!employees.contains(person)) {
	        throw new IllegalArgumentException("Die Person ist kein Mitarbeiter dieser Firma.");
	    }
	    person.setCompany(null);
	}
	
	/*package*/ void internalRemoveEmployee(Person person) {
	    employees.remove(person);
	}
	
	@PreRemove
    public void preRemove() {
	    final Collection<Person> employees = new HashSet<Person>(getEmployees());
	    for (Person person : employees) {
            removeEmployee(person);
        }
	}

	/* /------------ Pattern für JPA bidirektionale Beziehung ------------ */ 
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
