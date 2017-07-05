package org.anderes.edu.soap;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.anderes.wsdl.helloservice.HelloWorldService;

@WebService(name = "HelloWorldService", targetNamespace = "http://www.examples.com/wsdl/HelloService.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class HelloWorldSoap implements HelloWorldService {

    @Override
    public String sayHello(String firstName) {
        return "Hello " + firstName;
    }

}
