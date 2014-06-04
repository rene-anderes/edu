package org.anderes.edu.employee.infrastructure.persistence;

import java.util.Set;
import javax.persistence.Query;

/**
 * Dieses Interface versteckt JPA-Provider spezifische Funktionen um
 * gegenebenfalls den JPA-Provider ersetzen zu können.<br>
 * Sollte dies notwendig werden, wird einefach eine andere Implementation
 * des Interfaces verwendet (mittels @Inject natürlich)
 */
public interface PersistenceHintProvider {

    /**
     * Es werden entsprechende Query-Hints gesetzt, damit der Query
     * als Read-Only ausgeföhrt wird.<br>
     * Wird dies durch den JPA-Provider nicht unterstötzt, geschieht 
     * nichts weiteres (keine Exception)
     * 
     * @param query
     *            JPA-Query
     */
    void addReadOnlyHintToQueryIfSupported(final Query query);

    /**
     * Mittels Batch-Fetch werden LAZY-Beziehungen aufgelöst. Mit
     * dieser Methode werden die entsprechenden Query-Hints gesetzt.
     * Somit werden mit dem Query alle Objektbeziehungen die als
     * Parameter übergeben wurden aufgelöst. Die ist effizienter
     * als später die Beziehungen nachzuladen.<br>
     * Wird dies durch den JPA-Provider nicht unterstützt, geschieht 
     * nichts weiteres (keine Exception)
     * 
     * @param query
     *            JPA-Query
     * @param resolvingPaths
     *            Pfad der Aufgelöst werden soll
     */
    void addBatchHintToQueryIfSupported(final Query query, final Set<String> resolvingPaths);
}
