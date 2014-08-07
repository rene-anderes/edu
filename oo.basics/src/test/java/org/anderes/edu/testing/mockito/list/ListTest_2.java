package org.anderes.edu.testing.mockito.list;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ListTest_2 {

    @Test
    public void mockito() {

        /*
         * Mock-Objekt von der Klasse bzw. Schnittstelle, die simuliert werden soll, erzeugen
         */
        List<String> mockedList = mock(List.class);

        /* Verhalten definieren */
        when(mockedList.get(0)).thenReturn("Mock");

        /* Mock-Objekt benutzen */
        assertEquals("Mock", mockedList.get(0));

        /*
         * Verifizieren ob das Mock-Objekt so benutzt wurde wie vorgesehen
         */
        verify(mockedList).get(0);
    }
}
