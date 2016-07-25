package org.anderes.edu.effective.domain;

import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.anderes.edu.effective.immutability.EmployeeBase;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Employee implements Cloneable, EmployeeBase<Project, Address> {

    private String[] responsibilities;
    private Address address;
    private TreeSet<Project> projects = new TreeSet<>();
    private int age;
    private String name;

    public void setResponsibilities(String[] respnsibilities) {
        this.responsibilities = respnsibilities;
        
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
    
    public int getAge() {
        return age;
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
    public String getName() {
        return name;
    }

    @Override
    public String[] getResponsibilities() {
        return responsibilities;
    }
    
    @Override
    public Employee clone() {
        Employee clone = null;
        try {
            clone = (Employee) super.clone();
            clone.name = name;
            clone.age = age;
            if (responsibilities != null) {
                clone.responsibilities = responsibilities.clone();
            }
            if (address != null) {
                clone.address = address.clone();
            }
            clone.projects = projects.stream().map(p -> p.clone()).collect(Collectors.toCollection(TreeSet::new));
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
                        .append(responsibilities, other.responsibilities)
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
                        .append(responsibilities)
                        .append(address)
                        .append(projects.toArray())
                        .toHashCode();
    }

}
