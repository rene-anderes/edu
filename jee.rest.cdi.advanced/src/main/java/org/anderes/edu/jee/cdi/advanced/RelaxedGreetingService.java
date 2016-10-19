package org.anderes.edu.jee.cdi.advanced;

@Relaxed
public class RelaxedGreetingService implements GreetingService {

    @Override
    public String sayHello() {
        return "Dear World";
    }

}
