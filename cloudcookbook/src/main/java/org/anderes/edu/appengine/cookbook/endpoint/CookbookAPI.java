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
        ownerDomain = "cookbook.appengine.edu.anderes.org",
        ownerName = "anderes.org",
        packagePath = "cloud/platform")
)
public class CookbookAPI {

    private RecipeRepository repository = new RecipeRepository();
    
    @ApiMethod(name = "recipeListShort")
    public List<RecipeShort> getRecipeCollection() {
        return repository.getRecipeCollection();
    }
}
