package org.anderes.edu.exam.practise.airplane;

import java.util.Collection;

/**
 * Schnittstelle für den Zugriff auf die Informationen eines Flugzeuges.<br>
 * Ein Flugzeug besitzt 1...n Sitzplätze.<br>
 * Jede Sitznummer kommt nur einmal vor.
 * 
 */
public interface Airplane {

    /**
     * Gibt den Flugzeugtyp zurück.
     * 
     * @return Flugzeugtyp
     */
    String getType();

    /**
     * Setzt dem Flugzeug einen entsprechenden Sitz.
     * 
     * @param seat
     *            Sitz
     */
    void setSeat(final Seat seat);

    /**
     * Liefert den entsprechenden Sitz zurück.
     * 
     * @param seatNumber
     *            Sitznummer
     * @return Zur Sitznummer passenden Sitz
     */
    Seat getSeat(int seatNumber);

    /**
     * Entfernt den entsprechende Sitz.
     * 
     * @param seatNumber
     *            Sitznummer die entfernt wird.
     */
    void removeSeat(int seatNumber);

    /**
     * Gibt eine Liste mit allen Sitzen des Flugzeugs zurück.
     * 
     * @return Liste mit Flugzeugsitzen
     */
    Collection<Seat> getSeats();
    
    /**
     * Gibt die Anzahl Sitze zurück die als Notausgangsitze markiert sind.
     * 
     * @return Anzahl Notausgangssitze
     */
    int getNumberOfEmergencySeats();
}
