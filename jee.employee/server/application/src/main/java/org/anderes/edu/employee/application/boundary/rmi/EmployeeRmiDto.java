package org.anderes.edu.employee.application.boundary.rmi;

import java.io.Serializable;

public class EmployeeRmiDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String lastName;
    private Double salary;
    private String jobTitle;


    public EmployeeRmiDto() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String fName) {
        this.firstName = fName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lName) {
        this.lastName = lName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(final Double salary) {
        this.salary = salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(final String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String toString() {
        return "Employee(" + id + ": " + lastName + ", " + firstName + ")";
    }
}
