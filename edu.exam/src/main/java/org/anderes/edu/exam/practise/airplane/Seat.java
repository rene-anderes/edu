package org.anderes.edu.exam.practise.airplane;

/**
 * Schnittstelle für den Zugriff auf die Informationen eines Sitzes innerhalb
 * eines Flugzeuges.
 * 
 */
public interface Seat {

    /**
     * Gibt die Sitznummer zurück.
     * 
     * @return Eindeutige Sitznummer
     */
    int getSeatNumber();

    /**
     * Gibt an, ob sich der Sitz beim Notausgang befindet.
     * 
     * @return {@code true}, wenn sich der Sitz beim Notausgang befindet,
     * 	       sonst {@code false}
     */
    boolean isEmergencyExit();
}
