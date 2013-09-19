package org.anderes.edu.dip.dataaccess;

import java.util.ArrayList;
import java.util.List;

import org.anderes.edu.dip.domain.DomainDataAccess;



public class DataAccess implements DomainDataAccess {

	@Override
	public List<String> getNames() {
		return new ArrayList<String>();
	}
}
