package org.anderes.edu.di;

import com.google.inject.AbstractModule;

public class CalcGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Service.class).to(PrimeNumberService.class);
    }

}
