package org.anderes.edu.effective.domain;

import java.util.ArrayList;
import java.util.Collection;

public class Employee {

    private String[] responsibilities;
    private Address address;
    private Collection<Project> projects = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public String[] getResponsibilities() {
        return responsibilities;
    }

}
