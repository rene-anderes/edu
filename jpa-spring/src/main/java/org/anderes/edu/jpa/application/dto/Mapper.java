package org.anderes.edu.jpa.application.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.anderes.edu.jpa.domain.Image;
import org.anderes.edu.jpa.domain.Ingredient;
import org.anderes.edu.jpa.domain.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public RecipeDto mapToRecipeDto(final Recipe recipe) {
        final RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getUuid());
        dto.setTitle(recipe.getTitle());
        dto.setPreample(recipe.getPreample());
        dto.setNoOfPeople(recipe.getNoOfPerson());
        dto.setIngredients(mapToIngredientsDto(recipe.getIngredients()));
        dto.setPreparation(recipe.getPreparation());
        dto.setRating(recipe.getRating());
        dto.setAddingDate(recipe.getAddingDateTime());
        dto.setEditingDate(recipe.getLastUpdateTime());
        dto.setTags(mapToTagsDto(recipe.getTags()));
        if (recipe.getImage() != null) {
            dto.setImage(recipe.getImage().getUrl());
        }
        return dto;
    }

    private List<String> mapToTagsDto(final Set<String> tags) {
        return new ArrayList<String>(tags);
    }

    private List<IngredientDto> mapToIngredientsDto(final List<Ingredient> ingredients) {
        final List<IngredientDto> dto = new ArrayList<IngredientDto>(ingredients.size());
        for (Ingredient i : ingredients) {
            dto.add(mapToIngredientDto(i));
        }
        return dto;
    }    
    
    private IngredientDto mapToIngredientDto(final Ingredient ingredient) {
        final IngredientDto dto = new IngredientDto();
        dto.setComment(ingredient.getAnnotation());
        dto.setDescription(ingredient.getDescription());
        dto.setPortion(ingredient.getQuantity());
        return dto;
    }

    public RecipeShortDto mapToRecipeShortDto(final Recipe recipe) {
        final RecipeShortDto dto = new RecipeShortDto();
        dto.setEditingDate(recipe.getLastUpdateTime());
        dto.setId(recipe.getUuid());
        dto.setTitle(recipe.getTitle());
        return dto;
    }

    public List<TagDto> mapToTagDto(final List<String> tagCollection) {
        final HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String tag : tagCollection) {
            if (map.containsKey(tag)) {
                map.put(tag, map.get(tag) + 1);
            } else {
                map.put(tag, 1);
            }
        }
        final List<TagDto> dto = new ArrayList<TagDto>();
        for (String key : map.keySet()) {
            dto.add(new TagDto(key, map.get(key)));
        }
        return dto;
    }

    public Recipe maoToRecipe(final RecipeDto recipeDto) {
        final Recipe recipe = new Recipe(recipeDto.getId());
        recipe.setTitle(recipeDto.getTitle());
        recipe.setPreample(recipeDto.getPreample());
        recipe.setNoOfPerson(recipeDto.getNoOfPeople());
        recipe.setPreparation(recipeDto.getPreparation());
        recipe.setRating(recipeDto.getRating());
        recipe.setAddingDate(new Date(recipeDto.getAddingDate()));
        recipe.setLastUpdate(new Date(recipeDto.getEditingDate()));
        for (String t : recipeDto.getTags()) {
            recipe.addTag(t);
        }
        final List<Ingredient> ingredients = mapToIngredients(recipeDto.getIngredients());
        for (Ingredient ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }
        if (StringUtils.isNoneEmpty(recipeDto.getImage())) {
            final Image image = new Image(recipeDto.getImage(), null);
            recipe.setImage(image);
        }
        return recipe;
    }
    
    private List<Ingredient> mapToIngredients(final List<IngredientDto> ingredientsDto) {
        final List<Ingredient> ingredients = new ArrayList<Ingredient>(ingredientsDto.size());
        for (IngredientDto dto : ingredientsDto) {
            ingredients.add(mapToIngredient(dto));
        }
        return ingredients;
    }

    private Ingredient mapToIngredient(final IngredientDto dto) {
        return new Ingredient(dto.getPortion(), dto.getDescription(), dto.getComment());
    } 
}
