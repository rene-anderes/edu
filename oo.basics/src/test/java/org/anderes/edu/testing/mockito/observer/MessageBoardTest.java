package org.anderes.edu.testing.mockito.observer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.anderes.edu.observer.MessageBoard;
import org.anderes.edu.observer.Student;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MessageBoardTest {

    private static MessageBoard messageBoard;
    private Student hans;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	messageBoard = new MessageBoard();
    }

    @Before
    public void setUp() throws Exception {
	hans = new Student();
    }

    @Test
    public void standardTest() {
	messageBoard.addObserver(hans);
	messageBoard.addNewMessage("Meldung");
    }
    
    @Test
    public void mockitoTest() {
	/* Mock-Objekt von der Klasse bzw. Schnittstelle,
	 * die simuliert werden soll, erzeugen */
	Student mock = mock(Student.class);
	
	messageBoard.addObserver(mock);
	messageBoard.addNewMessage("Meldung");
	
	/* Verifizieren ob das Mock-Objekt 
	 * so benutzt wurde wie vorgesehen */
	verify(mock).update(messageBoard, "Meldung");
    }
}
