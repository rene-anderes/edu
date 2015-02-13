package ch.vrsg.edu.webservice.application.ws;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import ch.vrsg.edu.webservice.application.Employee;
import ch.vrsg.edu.webservice.application.EmployeeFacade;
import ch.vrsg.edu.webservice.application.EmployeeNotFoundException;

@WebService(portName="EmployeePort", name = "EmployeeService", serviceName = "EmployeeService", targetNamespace = "http://xmlns.intra.vrsg.ch/xmlns/employee/1")
public class EmployeeWebservice {

    @Inject
    private EmployeeFacade facade;
    
    @WebMethod(operationName = "find-employee")
    @WebResult(name = "employee")
    public Employee findEmployee(@WebParam(name="firstname") final String firstname, @WebParam(name="lastname") final String lastname) throws EmployeeNotFoundException {

        return facade.findEmployee(firstname, lastname);
    }
}
