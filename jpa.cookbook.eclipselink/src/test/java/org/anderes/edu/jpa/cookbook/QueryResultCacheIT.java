package org.anderes.edu.jpa.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.anderes.edu.jpa.cookbook.Recipe;
import org.anderes.edu.jpa.cookbook.RecipeRepositoryAlternative;
import org.junit.Before;
import org.junit.Test;

/**
 * Dieser Test zeigt, wie mit aktiviertem Query Results Cache von EclipseLink
 * Daten einer Query in den Shared Cache abgelegt werden und beim zweiten
 * Zugriff mittels der gleichen Query keine Daten mehr von der Datenbank 
 * gelesen werden. Siehe dazu die Eclipselink Doc:<br>
 * https://wiki.eclipse.org/EclipseLink/UserGuide/JPA/Basic_JPA_Development/Caching/Query_Cache
 * 
 * @author René Anderes
 *
 */
public class QueryResultCacheIT {

    private RecipeRepositoryAlternative repository;
    
    @Before
    public void setup() {
        repository = RecipeRepositoryAlternative.build();
    }
    
    /**
     * Ist in der Konfiguration der SharedCache aktiviert 
     * (persistence.xml -> {@code <shared-cache-mode>ALL</shared-cache-mode>}),
     * dann wird eine zweite Abfrage keinen Zugriff auf die Datenbank
     * machen müssen, da der Query Result Cache bei dieser Abfrage
     * eingeschaltet ist. (Lässt sich leider mit asserts nicht überprüfen)
     * Bitte Eclipselink SQL-Log daraufhin überprüfen. 
     */
    @Test
    public void shouldBeFindRecipesByTitle() {
        Collection<Recipe> recipes = repository.getRecipesByTitle("Dies");
        assertNotNull(recipes);
        assertThat(recipes.size(), is(1));
        
        recipes = repository.getRecipesByTitle("Dies");
        assertNotNull(recipes);
        assertThat(recipes.size(), is(1));

    }
}
