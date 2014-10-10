package org.anderes.edu.jpa.application;

import javax.inject.Inject;

import org.anderes.edu.jpa.domain.Recipe;
import org.anderes.edu.jpa.domain.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Inject 
    private RecipeRepository repository;

    @RequestMapping(method = RequestMethod.GET, produces={"application/json"})
    public @ResponseBody Page<Recipe> showRecipes(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @RequestMapping("/{id}")
    public @ResponseBody Recipe showUserForm(@PathVariable("id") Recipe recipe) {
        return recipe;
    }
}
