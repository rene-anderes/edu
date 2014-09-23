package org.anderes.edu.jee.cdi.advanced;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;

@Specializes @Alternative
public class AlternativeGreetingService extends RelaxedGreetingService {

    @Override
    public String sayHello() {
        return "Hallo Welt";
    }

}
