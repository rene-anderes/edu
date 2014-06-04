/*
 * Copyright (c) 2013 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package org.anderes.edu.employee.application.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.dto.EmployeeSalary;
import org.anderes.edu.employee.domain.Employee;

/**
 * Beispielimplementation um via RMI auf die Facade zugrieffen zu können.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class EmployeeFacadeRmi implements EmployeeFacadeRemote {

    @EJB
    private EmployeeFacade facade;
     
    @Override
    public List<EmployeeSalary> getSalaryList(final Double salary) {
        
        final List<EmployeeSalary> salaryList = new ArrayList<EmployeeSalary>();
        final List<Employee> employees = facade.findEmployeeBySalary(salary);
        final String currency = "CHF";
        
        for (Employee employee : employees) {
            String jobTitle = "";
            if (employee.getJobTitle() != null) {
                jobTitle = employee.getJobTitle().getTitle();
            }
            System.out.println(employee.getPhoneNumbers().size());
            EmployeeSalary entry = 
                new EmployeeSalary(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getSalary(), currency, jobTitle);
            salaryList.add(entry);
        }
        
        return salaryList;
    }

}
