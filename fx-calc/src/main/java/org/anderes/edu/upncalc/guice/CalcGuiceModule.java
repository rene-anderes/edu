package org.anderes.edu.upncalc.guice;

import java.util.ResourceBundle;

import org.anderes.edu.upncalc.HelpDialog;
import org.anderes.edu.upncalc.PrimeNumberService;
import org.anderes.edu.upncalc.Service;
import org.anderes.edu.upncalc.util.SystemInfoService;
import org.anderes.edu.upncalc.util.Utf8Control;

import com.google.inject.AbstractModule;

public class CalcGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Service.class).to(PrimeNumberService.class);
        bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle("CalcLanguagePack", new Utf8Control()));
        bind(SystemInfoService.class).toInstance(new SystemInfoService());
        bind(HelpDialog.class).asEagerSingleton();
    }

}
