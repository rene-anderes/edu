package org.anderes.edu.dip.domain;

import java.util.List;

import org.anderes.edu.dip.view.ViewDomainAccess;



public class DomainFunction implements ViewDomainAccess {

	private DomainDataAccess dataaccess;

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

//
//	public void bad() {
//		ViewImpl v = new ViewImpl(null);
//	}
}
