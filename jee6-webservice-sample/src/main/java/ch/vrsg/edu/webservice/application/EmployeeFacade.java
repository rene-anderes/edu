package ch.vrsg.edu.webservice.application;

import java.util.HashSet;
import java.util.Set;

public class EmployeeFacade {
    
    private Set<Employee> employees;
    
    public EmployeeFacade() {
        employees = new HashSet<>();
        employees.add(Employee.build("Leonardo", "Da Vinci"));
    }

    public Employee findEmployee(String firstname, String lastname) throws EmployeeNotFoundException {

        for (Employee e : employees) {
            if (e.getFirstname().equals(firstname) && e.getLastname().equals(lastname)) {
                return e;
            }
        }
        throw new EmployeeNotFoundException(firstname, lastname);
    }

}
