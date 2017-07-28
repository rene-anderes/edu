package org.anderes.edu.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(
                name = "BmiSOAPServices",       // Endpoint Name 
                serviceName = "BmiService",     // Service Name - WSDL: .../BmiService?wsdl
                portName = "BmiPort",           // Port Name
                targetNamespace = "http://superbiz.org/wsdl"
)
public class BmiWebService {

    @WebMethod(operationName = "body-mass-index")
    @WebResult(name = "your-bmi")
    public double bmi(@WebParam(name = "height") double height, @WebParam(name = "weight") double weight) {
        return weight / ((height * height) / 10000);
    }
}
