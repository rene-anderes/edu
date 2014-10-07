package org.anderes.edu.jpa.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Collection<Recipe> findByTitleLike(String string);
    
    List<String> findAllTag();

}
