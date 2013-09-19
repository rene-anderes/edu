package org.anderes.edu.nodip.view;

import org.anderes.edu.nodip.domain.DomainFunction;


public class View {

	private DomainFunction function;

	public View() {
		this.function = new DomainFunction();
		this.function.getName("Test");
	}
}
