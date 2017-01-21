package org.anderes.edu.di.spring;

import org.anderes.edu.di.Calc;
import org.anderes.edu.di.PrimeNumberService;
import org.anderes.edu.di.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CalcConfigSpring {
    
    @Bean
    public Service primeNumberService() {
        return new PrimeNumberService();
    }

    /* The default scope is singleton, but you can override this with the @Scope annotation as follows: */
    @Bean 
    @Scope("prototype")
    public Calc calc() {
        return new Calc();
    }
}
