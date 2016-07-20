package org.anderes.edu.jpa.cookbook.solution1;

/**
 * Immutable Objekt für die Liste aller Rezepte.
 * 
 * @author René Anderes
 *
 */
public class RecipeShort {

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
