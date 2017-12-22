package ch.vrsg.edu.webservice.application.ws;

import java.util.Collection;

import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.sun.xml.ws.developer.SchemaValidation;

import ch.vrsg.edu.webservice.application.Employee;
import ch.vrsg.edu.webservice.application.EmployeeFacade;
import ch.vrsg.edu.webservice.application.EmployeeNotFoundException;

@WebService(
        name = "EmployeeService", 
        serviceName = "EmployeeService", 
        portName="EmployeePort", 
        targetNamespace = "http://xmlns.intra.vrsg.ch/xmlns/employee/1")
@HandlerChain(file = "handlers.xml")
@SchemaValidation
public class EmployeeWebservice {
    
    @Inject
    private EmployeeFacade facade;
    
    @WebMethod(operationName = "find-employee")
    @WebResult(name = "employee")
    public Employee findEmployee(@WebParam(name="firstname") final String firstname, 
                    @WebParam(name="lastname") final String lastname) throws EmployeeNotFoundException {

        return facade.findEmployee(firstname, lastname);
    }
    
    @WebMethod(operationName = "find-all")
    @WebResult(name = "employees")
    public Collection<Employee> findAll() {
        return facade.findAll();
    }
}
