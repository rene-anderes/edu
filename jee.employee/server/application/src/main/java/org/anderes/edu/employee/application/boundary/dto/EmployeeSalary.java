/*
 * Copyright (c) 2011 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package org.anderes.edu.employee.application.boundary.dto;

import java.io.Serializable;



public class EmployeeSalary implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Double salary;
    private final String currency;
    private final String jobTitle;

    public EmployeeSalary(final Long id, final String firstName, final String lastName, final Double salary, final String currency, final String jobTitle) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.currency = currency;
        this.jobTitle = jobTitle;
    }
    
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCurrency() {
        return currency;
    }
    
}
