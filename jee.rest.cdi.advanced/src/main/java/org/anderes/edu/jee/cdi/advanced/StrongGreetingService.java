package org.anderes.edu.jee.cdi.advanced;

import javax.enterprise.inject.Default;

@Default
public class StrongGreetingService implements GreetingService {

    @Override
    public String sayHello() {
        return "Hello World";
    }

}
