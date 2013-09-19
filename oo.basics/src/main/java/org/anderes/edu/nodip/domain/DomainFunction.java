package org.anderes.edu.nodip.domain;

import java.util.List;

import org.anderes.edu.nodip.dataaccess.DataAccess;



public class DomainFunction {

	private DataAccess dataaccess;

	public DomainFunction() {
		this.dataaccess = new DataAccess();
	}

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
