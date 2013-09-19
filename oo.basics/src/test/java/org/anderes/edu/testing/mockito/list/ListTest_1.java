package org.anderes.edu.testing.mockito.list;

import java.util.List;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class ListTest_1 {

    @Test
    public void mockito() {
	/* Mock-Objekt von der Klasse bzw. Schnittstelle,
	 * die simuliert werden soll, erzeugen */
	List<String> mockedList = mock(List.class);
	
	/* Mock-Objekt benutzen */
	mockedList.add("one");
	mockedList.clear();
	
	/* Verifizieren ob das Mock-Objekt 
	 * so benutzt wurde wie vorgesehen */
	verify(mockedList).add("one");
	verify(mockedList).clear();
    }
}
