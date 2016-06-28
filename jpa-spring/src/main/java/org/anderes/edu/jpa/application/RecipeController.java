package org.anderes.edu.jpa.application;

import javax.inject.Inject;

import org.anderes.edu.jpa.domain.Recipe;
import org.anderes.edu.jpa.domain.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.springframework.http.MediaType.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("recipes")
public class RecipeController {

    @Inject 
    private RecipeRepository repository;

    @RequestMapping(method = RequestMethod.GET, produces={APPLICATION_JSON_VALUE})
    public @ResponseBody Page<Recipe> showRecipes(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @RequestMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody Recipe showRecipe(@PathVariable("id") Recipe recipe) {
        return repository.findOne(recipe.getUuid());
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes={APPLICATION_JSON_VALUE}, produces={APPLICATION_JSON_VALUE})
    public @ResponseBody Recipe saveRecipe(@RequestBody Recipe recipe) {
        return repository.save(recipe);
    }
}
