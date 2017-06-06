package org.anderes.edu.soap;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.examples.wsdl.helloservice.HelloPortType;

@WebService(name = "Hello_PortType", targetNamespace = "http://www.examples.com/wsdl/HelloService.wsdl")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class HelloWorldSoap implements HelloPortType {

    @Override
    public String sayHello(String firstName) {
        return "Hello " + firstName;
    }

}
