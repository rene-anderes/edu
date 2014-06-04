package org.anderes.edu.employee.domain;


/**
 * Interface für alle Repositories
 * 
 * @param <T>
 *            Entity
 * @param <ID>
 *            Datenbankidentität
 */
public interface Repository<T, ID> {

    /**
     * Liefert anhand des Primary Key die Entität zurück.
     * 
     * @param id
     *            Primary Key der Entität
     * @return Die Instanz der Entität
     */
    T findOne(final ID id);

    /**
     * Speichert die übergebene Entität
     * 
     * @param entity
     *            Entität (detached oder attached)
     * @return gespeicherte Entität (attached)
     */
    T save(final T entity);

    /**
     * Löscht die entsprechende Entität
     * 
     * @param entity
     *            Entität
     */
    void delete(final T entity);

    /**
     * Gibt an, ob eine Entität mit dem entsprechenden Primary Key existiert.
     * 
     * @param id
     *            Primary Key der Entität
     * @return {@code true}, wenn die Entität existiert, sonst {@code false}
     */
    boolean exists(final ID id);
}
