package org.anderes.edu.guice.domain;

import java.util.List;

import org.anderes.edu.guice.view.ViewDomainAccess;

import com.google.inject.Inject;


public class DomainFunction implements ViewDomainAccess {

	private DomainDataAccess dataaccess;

    @Inject
	public DomainFunction(DomainDataAccess dataaccess) {
		this.dataaccess = dataaccess;
	}

	@Override
	public String getName(String key) {
		List<String> names = dataaccess.getNames();
		for (String name : names) {
			if (name.matches(".*" + key + ".*")) {
				return name;
			}
		}
		return "";
	}
}
