package org.anderes.edu.testing.easymock.list;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;

public class ListTest_4 {

    @Test
    public void sample() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt erzeugen
        List<String> mockedList = createStrictMock(List.class);
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Verhalten / Benutzung aufzeichnen
        expect(mockedList.add("one")).andReturn(true);
        expect(mockedList.add("two")).andReturn(true);
        mockedList.clear();
        replay(mockedList);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Test's durchf�hren
        assertTrue(mockedList.add("one"));  // Reihenfolge spielt ist einzuhalten -> createStrictMock()
        assertTrue(mockedList.add("two"));
        mockedList.clear();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt verifizieren
        verify(mockedList);
    }
}
