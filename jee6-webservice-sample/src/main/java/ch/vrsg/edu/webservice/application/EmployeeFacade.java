package ch.vrsg.edu.webservice.application;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;

public class EmployeeFacade {
    
    private Logger logger;
    private Set<Employee> employees;
    
    public EmployeeFacade() {
        employees = new HashSet<>();
        employees.add(Employee.build("Leonardo", "Da Vinci"));
        employees.add(Employee.build("Maria", "Magdalena"));
        employees.add(Employee.build("Mona Lisa", "Medico"));
    }

    @Inject
    /*packeg*/ void setLogger(final Logger logger) {
        this.logger = logger;
    }
    
    @Audit
    public Employee findEmployee(String firstname, String lastname) throws EmployeeNotFoundException {
        
        final Employee soughtAfterEmployee = Employee.build(firstname, lastname);
        Optional<Employee> findEmployee = employees.stream().filter(employee -> employee.equals(soughtAfterEmployee)).findFirst();
        if (findEmployee.isPresent()) {
            return findEmployee.get();
        }
        logger.info("Mitarbeiter nicht gefunden.");
        throw new EmployeeNotFoundException(firstname, lastname);
    }
    
    public Collection<Employee> findAll() {
        return employees;
    }

}
