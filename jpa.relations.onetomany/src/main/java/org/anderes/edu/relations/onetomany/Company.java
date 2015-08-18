package org.anderes.edu.relations.onetomany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
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
	
	@OneToMany(mappedBy="company")
	private Collection<Person> employees = new ArrayList<Person>();

	private String name;

	/*package*/ Company() {};
	
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

	public void addEmployee(final Person person) {
		if (!employees.contains(person)) {
			employees.add(person);
		}
	}
	
	public void removeEmployee(final Person person) {
		employees.remove(person);
	}

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
