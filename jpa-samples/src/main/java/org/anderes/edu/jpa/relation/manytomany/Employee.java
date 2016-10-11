package org.anderes.edu.jpa.relation.manytomany;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Beispiel welches die Möglichkeiten der Basic Attributes veranschaulicht.
 */
@Entity
public class Employee {

	@Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;
    
	@Column(name = "F_NAME", nullable = false, length = 200)
    private String firstname;
	
	@Column(name = "L_NAME", nullable = false, length = 200)
    private String lastname;
	
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

    public void addProject(final Project project) {
        projects.add(project);
    }
    
    public Collection<Project> getProjects() {
        return Collections.unmodifiableSet(projects);
    }
}
