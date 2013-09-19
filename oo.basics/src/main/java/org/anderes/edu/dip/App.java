package org.anderes.edu.dip;

import org.anderes.edu.dip.dataaccess.DataAccess;
import org.anderes.edu.dip.domain.DomainDataAccess;
import org.anderes.edu.dip.domain.DomainFunction;
import org.anderes.edu.dip.view.View;
import org.anderes.edu.dip.view.ViewDomainAccess;




public class App {

	public static void main(String[] args) {
		DomainDataAccess data = new DataAccess();
		ViewDomainAccess domain = new DomainFunction(data);
		new View(domain);
		
	}

}
