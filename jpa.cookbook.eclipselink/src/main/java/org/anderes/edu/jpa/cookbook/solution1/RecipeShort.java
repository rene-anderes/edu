package org.anderes.edu.jpa.cookbook.solution1;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Immutable Objekt für die Liste aller Rezepte.
 * <p>
 * Diese Entität wird nicht in der Datenban gespeichert.
 * Sie dient dazu, ein Rezept mit "abgespeckten" Informationen
 * verarbeiten zu können. Dazu wird der 'JPQL Constructor Expressions' NEW
 * verwendet.
 * 
 * @author René Anderes
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "RecipeShort.ByIngredient", 
        query = "Select NEW org.anderes.edu.jpa.cookbook.solution1.RecipeShort(r.id, r.title, r.preamble) "
                        + "from Recipe r join r.ingredients i where i.description like :description")
})
public class RecipeShort {

    @Id
    private long id;
    private String title;
    private String preamble;

    RecipeShort() {
        super();
    }
    
    /**
     * Konstruktor für die JPQL 'RecipeShort.ByIngredient' 
     */
    public RecipeShort(long id, String title, String preamble) {
        this.id = id;
        this.title = title;
        this.preamble = preamble;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPreamble() {
        return preamble;
    }
}
