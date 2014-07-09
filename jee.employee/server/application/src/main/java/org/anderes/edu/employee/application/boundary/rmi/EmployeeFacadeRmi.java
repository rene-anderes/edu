package org.anderes.edu.employee.application.boundary.rmi;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
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
    @Inject
    private DtoMapper mapper;
     
    @Override
    public List<EmployeeDto> getSalaryList(final Double salary) {
        
        final List<Employee> employees = facade.findEmployeeBySalary(salary);
        return mapper.mapToEmployeeDtoCollection(employees);
    }

}
