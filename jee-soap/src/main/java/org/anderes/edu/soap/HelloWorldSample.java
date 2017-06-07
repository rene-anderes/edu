package org.anderes.edu.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HelloWorldSample {

    @WebMethod
    public String getHelloWorldAsString(String name) {
      return "Hello World JAX-WS " + name;
    }

    @WebMethod
    public Person getHelloWrldAsPerson(String name) {
        final Person person = new Person();
        person.setName(name);
        return person;
    }
}
