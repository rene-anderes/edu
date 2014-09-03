package org.anderes.edu.employee.application.boundary.rmi;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.anderes.edu.employee.application.EmployeeFacade;
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
    public List<EmployeeRmiDto> getSalaryList(final Double salary) {
        
        return mapToDtoCollection(facade.findEmployeeBySalary(salary));
    }

    private List<EmployeeRmiDto> mapToDtoCollection(final List<Employee> employees) {
        final List<EmployeeRmiDto> collection = new ArrayList<>();
        for (Employee employee : employees) {
            collection.add(mapToDto(employee));
        }
        return collection;
    }
    
    private EmployeeRmiDto mapToDto(final Employee employee) {
        final EmployeeRmiDto dto = new EmployeeRmiDto();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setSalary(employee.getSalary());
        if (employee.getJobTitle() != null) {
            dto.setJobTitle(employee.getJobTitle().getTitle());
        } else {
            dto.setJobTitle("");
        }
        return dto;
    }
}
