package org.anderes.edu.testing.easymock.list;

import java.util.List;

import org.junit.Test;

import static org.easymock.EasyMock.*;

public class ListTest_1 {

    @Test
    public void sample() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt erzeugen
        List<String> mockedList = createMock(List.class);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Verhalten / Benutzung aufzeichnen
        mockedList.add(0, "one");
        replay(mockedList);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Test's durchführen
        mockedList.add(0, "one");
    }
}
