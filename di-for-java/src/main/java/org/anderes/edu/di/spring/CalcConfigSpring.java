package org.anderes.edu.di.spring;

import org.anderes.edu.di.Calc;
import org.anderes.edu.di.PrimeNumberService;
import org.anderes.edu.di.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalcConfigSpring {
    
    @Bean
    public Service primeNumberService() {
        return new PrimeNumberService();
    }

    @Bean
    public Calc calc() {
        return new Calc();
    }
}
