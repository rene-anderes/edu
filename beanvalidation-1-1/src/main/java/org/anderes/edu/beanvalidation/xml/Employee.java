package org.anderes.edu.beanvalidation.xml;

import java.math.BigDecimal;

public class Employee {

    protected String lastname;
    protected String firstname;
    protected String jobtitle;
    protected BigDecimal salary;
    protected String gender;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String value) {
        this.lastname = value;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String value) {
        this.firstname = value;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String value) {
        this.jobtitle = value;
    }

    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal value) {
        this.salary = value;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String value) {
        this.gender = value;
    }
}
