package org.anderes.edu.di.guice;

import org.anderes.edu.di.Calc;
import org.anderes.edu.di.PrimeNumberService;
import org.anderes.edu.di.Service;

import com.google.inject.AbstractModule;

public class CalcGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Service.class).to(PrimeNumberService.class);
        bind(Calc.class).toInstance(new Calc());
    }

}
