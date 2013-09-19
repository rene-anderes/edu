package org.anderes.edu.guice.view;

import com.google.inject.Inject;


public class View {

	private ViewDomainAccess function;

	@Inject
	public View(ViewDomainAccess function) {
		this.function = function;
		this.function.getName("Test");
	}
}
