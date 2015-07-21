package ch.vrsg.edu.webservice.application;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;

public class EmployeeFacade {
    
    @Inject
    private Logger logger;
    private Set<Employee> employees;
    
    public EmployeeFacade() {
        employees = new HashSet<>();
        employees.add(Employee.build("Leonardo", "Da Vinci"));
        employees.add(Employee.build("Maria", "Magdalena"));
        employees.add(Employee.build("Mona Lisa", "Medico"));
    }

    @Audit
    public Employee findEmployee(String firstname, String lastname) throws EmployeeNotFoundException {

        for (Employee e : employees) {
            if (e.getFirstname().equals(firstname) && e.getLastname().equals(lastname)) {
                return e;
            }
        }
        logger.info("Mitarbeiter nicht gefunden.");
        throw new EmployeeNotFoundException(firstname, lastname);
    }
    
    public Collection<Employee> finaAll() {
        return employees;
    }

}
