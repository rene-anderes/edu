/*
 * Copyright (c) 2013 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package org.anderes.edu.employee.application;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.EmployeeRepository;
import org.apache.commons.lang3.Validate;

@Stateless
public class EmployeeFacade {

    @Inject
    private EmployeeRepository repository;

    /**
     * Leifert alle Mitarbeieter die mehr als den übergebenen Jahreslohn verdienen.
     * 
     * @param salary
     *            Jahreslohn
     * @return Liste mit Mitarbeitern
     */
    public List<Employee> findEmployeeBySalary(final Double salary) {
        Validate.notNull(salary, "Der Parameter 'salary' darf nicht null sein.");
        Validate.isTrue(salary.doubleValue() > 0.0, "Der Wert muss grösser als 0 sein: %s", salary.doubleValue());
        
        return repository.findEmployeeBySalary(salary);
    }
    
    public Employee findOne(final Long employeeId) {
        Validate.notNull(employeeId, "Der Parameter 'employeeId' darf nicht null sein.");
        
        return repository.findOne(employeeId);
    }
}
