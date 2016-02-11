package ch.vrsg.edu.webservice.application;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

@Stateless
public class EmployeeFacade {
    
    private Logger logger;
    private Set<Employee> employees;
    
    public EmployeeFacade() {
        employees = new HashSet<>();
        employees.add(Employee.build("Leonardo", "Da Vinci", 1));
        employees.add(Employee.build("Maria", "Magdalena", 2));
        employees.add(Employee.build("Mona Lisa", "Medico", 3));
    }

    @Inject
    /*packeg*/ void setLogger(final Logger logger) {
        this.logger = logger;
    }
    
    @Audit
    @Performance
    public Employee findEmployee(String firstname, String lastname) throws EmployeeNotFoundException {
        
        final Employee soughtAfterEmployee = Employee.build(firstname, lastname, 0);
        Optional<Employee> findEmployee = employees.stream().filter(employee -> employee.equals(soughtAfterEmployee)).findFirst();
        if (findEmployee.isPresent()) {
            return findEmployee.get();
        }
        logger.info("Mitarbeiter nicht gefunden.");
        throw new EmployeeNotFoundException(firstname, lastname);
    }
    
    @Audit
    public Integer save(Employee employee) {
        final Integer id = employees.size() + 1;
        employees.add(Employee.build(employee.getFirstname(), employee.getLastname(), id));
        return id;
    }
    
    public Collection<Employee> findAll() {
        return employees;
    }

}
