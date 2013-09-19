package org.anderes.edu.guice;

import org.anderes.edu.guice.dataaccess.DataAccess;
import org.anderes.edu.guice.domain.DomainDataAccess;
import org.anderes.edu.guice.domain.DomainFunction;
import org.anderes.edu.guice.view.ViewDomainAccess;

import com.google.inject.AbstractModule;


public class AppModule extends AbstractModule {

    @Override
    protected void configure() {

        /* Binding einer Implementation an das Interface */

        bind(DomainDataAccess.class).to(DataAccess.class);
        bind(ViewDomainAccess.class).to(DomainFunction.class);
    }
}