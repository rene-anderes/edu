package org.anderes.edu.upncalc.guice;

import java.util.ResourceBundle;

import org.anderes.edu.upncalc.PrimeNumberService;
import org.anderes.edu.upncalc.Service;

import com.google.inject.AbstractModule;

public class CalcGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Service.class).to(PrimeNumberService.class);
        bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle("CalcLanguagePack"));
    }

}
