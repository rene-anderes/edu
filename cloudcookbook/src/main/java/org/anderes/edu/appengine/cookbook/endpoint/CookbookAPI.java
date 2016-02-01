package org.anderes.edu.appengine.cookbook.endpoint;

import java.util.List;

import org.anderes.edu.appengine.cookbook.RecipeRepository;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

@Api(
    name = "cookbookApi",
    version = "v1",
    namespace = @ApiNamespace(
        ownerDomain = "endpoint.cookbook.appengine.edu.anderes.org",
        ownerName = "endpoint.cookbook.appengine.edu.anderes.org",
        packagePath = "")
)
public class CookbookAPI {

    private RecipeRepository repository = new RecipeRepository();
    
    @ApiMethod(name = "getAllRecipesShort")
    public List<RecipeShort> getRecipeCollection() {
        return repository.getRecipeCollection();
    }
}
