package org.anderes.edu.effecitive.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Employee implements Cloneable {

    private String[] respnsibilities;
    private Address address;
    private HashSet<Project> projects = new HashSet<>();
    private int age;
    private String name;

    public void setResponsibilities(String[] respnsibilities) {
        this.respnsibilities = respnsibilities;
        
    }
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Project> getProjects() {
        return projects;
    }
    
    public void removeProject(Project project) {
        projects.remove(project);
    }

    @Override
    public Employee clone() {
        Employee clone = null;
        try {
            clone = (Employee) super.clone();
            clone.name = name;
            clone.age = age;
            if (respnsibilities != null) {
                clone.respnsibilities = respnsibilities.clone();
            }
            if (address != null) {
                clone.address = address.clone();
            }
            clone.projects = projects.stream().map(p -> p.clone()).collect(Collectors.toCollection(HashSet::new));
            return clone;
        } catch (CloneNotSupportedException e) { }  // Won't happen
        return clone;
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
        Employee other = (Employee) obj;
        return new EqualsBuilder()
                        .append(respnsibilities, other.respnsibilities)
                        .append(address, other.address)
                        .append(projects.toArray(), other.projects.toArray())
                        .append(age, other.age)
                        .append(name, other.name)
                        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                        .append(name)
                        .append(age)
                        .append(respnsibilities)
                        .append(address)
                        .append(projects.toArray())
                        .toHashCode();
    }
}
