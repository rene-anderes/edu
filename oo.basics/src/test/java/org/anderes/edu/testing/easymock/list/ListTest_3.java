package org.anderes.edu.testing.easymock.list;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;

public class ListTest_3 {

    @Test
    public void sample() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt erzeugen
        List<String> mockedList = createMock(List.class);
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Verhalten / Benutzung aufzeichnen
        expect(mockedList.add("one")).andReturn(true);
        expect(mockedList.add("two")).andReturn(true);
        mockedList.clear();
        replay(mockedList);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Test's durchführen
        assertTrue(mockedList.add("two"));
        assertTrue(mockedList.add("one"));  // Reihenfolge spielt hier keine Rolle -> createMock()
        mockedList.clear();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt verifizieren
        verify(mockedList);
    }
}
