package org.anderes.edu.testing.easymock.list;

import java.util.List;

import org.junit.Test;

import static org.easymock.EasyMock.*;

public class ListTest_2 {

    @Test
    public void sample() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt erzeugen
        List<String> mockedList = createMock(List.class);
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Verhalten / Benutzung aufzeichnen
        mockedList.add(0, "one");
        mockedList.clear();
        replay(mockedList);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Test's durchführen
        mockedList.add(0, "one");
        mockedList.clear();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt verifizieren
        // Überprüft, ob alle erwarteten Methoden aufgerufen werden
        verify(mockedList);
    }
}
