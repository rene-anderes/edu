package org.anderes.edu.jpa.cookbook.solution1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.persistence.Cache;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

public class SharedCacheIT {
    
    private RecipeRepositoryEntityManager repository;
    
    @Before
    public void setup() {
        repository = RecipeRepositoryEntityManager.build();
    }

    /**
     * Ist in der Konfiguration der SharedCache aktiviert 
     * (persistence.xml -> {@code <shared-cache-mode>ALL</shared-cache-mode>}),
     * dann wird eine Entity auch nach dem Schliessen des Entity-Managers
     * (1L Cache) im Shared-Cache zu finden sein.
     * <p>
     * Lässt sich leider mit asserts nicht überprüfen.
     * Bitte Eclipselink SQL-Log daraufhin überprüfen. 
     */
    @Test
    public void shouldBeSharedCache() {
        
        final Recipe recipe = repository.findOne(10001l);

        final Cache sharedL2Cache = Persistence.createEntityManagerFactory("eclipseLinkPU").getCache();
        assertThat(sharedL2Cache.contains(Recipe.class, recipe.getId()), is(true));
        
        // diese Anfrage wird aus dem 2L Cache beantwortet
        final Recipe cachedRecipe = repository.findOne(10001l);
        assertThat(cachedRecipe.getTitle(), is(recipe.getTitle()));        
    }
}
