package org.anderes.edu.employee.application.boundary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.domain.Employee;

/**
 * Beispielimplementation um via RMI auf die Facade zugreifen zu können.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class EmployeeFacadeRmi implements EmployeeFacadeRemote {

    @EJB
    private EmployeeFacade facade;
     
    @Override
    public List<EmployeeDto> getSalaryList(final Double salary) {
        
        final List<EmployeeDto> salaryList = new ArrayList<>();
        final List<Employee> employees = facade.findEmployeeBySalary(salary);
        
        for (Employee employee : employees) {
            final EmployeeDto entry = new EmployeeDto();
            if (employee.getJobTitle() != null) {
                entry.setJobtitle(employee.getJobTitle().getTitle());
            }
            entry.setSalary(new BigDecimal(employee.getSalary()));
            entry.setFirstname(employee.getFirstName());
            entry.setLastname(employee.getLastName());
            salaryList.add(entry);
        }
        
        return salaryList;
    }

}
