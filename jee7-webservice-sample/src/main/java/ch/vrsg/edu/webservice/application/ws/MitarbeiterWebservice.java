package ch.vrsg.edu.webservice.application.ws;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import ch.vrsg.edu.webservice.application.Employee;
import ch.vrsg.edu.webservice.application.EmployeeFacade;
import ch.vrsg.edu.webservice.application.EmployeeNotFoundException;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.FindeMitarbeiterRequest;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.FindeMitarbeiterResponse;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.MitarbeiterNotFound;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.MitarbeiterNotFoundException;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.MitarbeiterService;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.ObjectFactory;

@WebService(
        name = "MitarbeiterService", 
        serviceName="MitarbeiterService", 
        portName="MitarbeiterPort",
        targetNamespace = "http://xmlns.intra.vrsg.ch/xmlns/mitarbeiter/1", 
        wsdlLocation = "WEB-INF/wsdl/mitarbeiter.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class MitarbeiterWebservice implements MitarbeiterService {

    @Inject
    private EmployeeFacade facade;

    @Override
    public FindeMitarbeiterResponse findeMitarbeiter(
                    @WebParam(name = "findeMitarbeiterRequest", targetNamespace = "http://xmlns.intra.vrsg.ch/xmlns/mitarbeiter/1", partName = "parameter") 
                    FindeMitarbeiterRequest parameter) throws MitarbeiterNotFoundException {
        final ObjectFactory factory = new ObjectFactory();
        try {
            final Employee employee = facade.findEmployee(parameter.getVorname(), parameter.getNachname());
            final FindeMitarbeiterResponse response = factory.createFindeMitarbeiterResponse();
            response.setVorname(employee.getFirstname());
            response.setNachname(employee.getLastname());
            return response;
        } catch (EmployeeNotFoundException e) {
            final MitarbeiterNotFound faultInfo = factory.createMitarbeiterNotFound();
            faultInfo.setMessage("Mitarbeiter nicht gefunden.");
            faultInfo.setVorname(parameter.getVorname());
            faultInfo.setNachname(parameter.getNachname());
            throw new MitarbeiterNotFoundException("Mitarbeiter nicht gefunden", faultInfo, e);
        }
    }

}
