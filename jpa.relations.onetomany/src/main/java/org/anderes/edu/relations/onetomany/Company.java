package org.anderes.edu.relations.onetomany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Company
 * 
 */
@Entity
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="company", cascade = CascadeType.ALL)
	private Collection<Person> employees = new ArrayList<Person>();

	public Company() {
		super();
	}

	public Collection<Person> getEmployees() {
		return Collections.unmodifiableCollection(employees);
	}

	public Long getId() {
		return id;
	}

	public void addEmployee(final Person person) {
		if (employees.contains(person)) {
			return;
		}
		employees.add(person);
		if (person.getCompany() != null) {
			person.getCompany().getEmployees().remove(person);
		}
		person.setCompany(this);
	}
	
	public void removeEmployee(final Person person) {
		employees.remove(person);
		person.setCompany(null);
	}

}
