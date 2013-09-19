package org.anderes.edu.dip.view;


public class View {

	private ViewDomainAccess function;

	public View(ViewDomainAccess function) {
		this.function = function;
		this.function.getName("Test");
	}
}
