package org.anderes.edu.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HelloWorldSample {

    @WebMethod
    public String getHelloWorldAsString(String name) {
      return "Hello World JAX-WS " + name;
    }

}
