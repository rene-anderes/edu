package org.anderes.edu.assoziation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Firma {

	private Collection<Person> arbeitnehmer = new HashSet<Person>();

	public Collection<Person> getArbeitnehmer() {
		return Collections.unmodifiableCollection(arbeitnehmer);
	}

}
