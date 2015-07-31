package org.anderes.edu.employee.application;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

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
        
        return repository.findEmployeeBySalaryFetchJobtitle(salary);
    }
    
    public Optional<Employee> findOne(final Long employeeId) {
        Validate.notNull(employeeId, "Der Parameter 'employeeId' darf nicht null sein.");
        try {
        	return Optional.of(repository.findOneEmployeeAddress(employeeId));
        } catch (NoResultException e) {
        	return Optional.empty();
        }
    }
    
    public List<Employee> findEmployees() {
        return repository.findEmployees();
    }

    public Optional<Employee> findProjectsByEmployee(final Long employeeId) {
        Validate.notNull(employeeId, "Der Parameter 'employeeId' darf nicht null sein.");
        try {
            return Optional.of(repository.findProjectsByEmployee(employeeId));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Employee saveEmployee(Employee employee) {
        Validate.notNull(employee, "Der Parameter 'employee' darf nicht null sein.");
        return repository.save(employee);
    }
}
