package org.anderes.edu.guice;

import org.anderes.edu.guice.view.View;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class App {

	public static void main(String[] args) {
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new AppModule());

	    /* Erzeugen der View */
	    View view = injector.getInstance(View.class);
	}

}
