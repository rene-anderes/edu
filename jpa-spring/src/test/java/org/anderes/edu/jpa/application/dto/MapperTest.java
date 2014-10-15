package org.anderes.edu.jpa.application.dto;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.anderes.edu.jpa.application.dto.IngredientDto;
import org.anderes.edu.jpa.application.dto.Mapper;
import org.anderes.edu.jpa.application.dto.RecipeDto;
import org.anderes.edu.jpa.application.dto.RecipeShortDto;
import org.anderes.edu.jpa.application.dto.TagDto;
import org.anderes.edu.jpa.domain.Ingredient;
import org.anderes.edu.jpa.domain.Recipe;
import org.anderes.edu.jpa.domain.RecipeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class MapperTest {

    @Inject
    private Mapper mapper;
    private Recipe recipe;
    private RecipeDto recipeDto;
    
    @Before
    public void setUp() throws Exception {
        recipe = RecipeBuilder.buildRecipe();
        recipeDto = RecipeDtoBuilder.buildRecipeDto();
    }

    @Test
    public void shouldBeMapToRecipeDto() {
        final RecipeDto dto = mapper.mapToRecipeDto(recipe);
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getId(), is(recipe.getUuid()));
        assertThat(dto.getTitle(), is(recipe.getTitle()));
        assertThat(dto.getPreample(), is(recipe.getPreample()));
        assertThat(dto.getNoOfPeople(), is(recipe.getNoOfPerson()));
        final List<IngredientDto> ingredientsDto = dto.getIngredients();
        assertThat(ingredientsDto.size(), is(3));
        for (int i = 0; i < 3; i++) {
            assertThat(ingredientsDto.get(i).getPortion(), is(recipe.getIngredients().get(i).getQuantity()));
            assertThat(ingredientsDto.get(i).getDescription(), is(recipe.getIngredients().get(i).getDescription()));
            assertThat(ingredientsDto.get(i).getComment(), is(recipe.getIngredients().get(i).getAnnotation()));
        }
        assertThat(dto.getPreparation(), is(recipe.getPreparation()));
        assertThat(dto.getRating(), is(recipe.getRating()));
        assertThat(dto.getAddingDate(), is(recipe.getAddingDateTime()));
        assertThat(dto.getEditingDate(), is(recipe.getLastUpdateTime()));
        assertThat(dto.getTags().size(), is(2));
        assertThat(dto.getTags().containsAll(recipe.getTags()), is(true));
        assertThat(dto.getImage(), is(recipe.getImage().getUrl()));
    }
    
    @Test
    public void shouldBeMapToRecipeShortDto() {
        final RecipeShortDto dto = mapper.mapToRecipeShortDto(recipe);
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getId(), is(recipe.getUuid()));
        assertThat(dto.getTitle(), is(recipe.getTitle()));
        assertThat(dto.getEditingDate(), is(recipe.getLastUpdateTime()));
    }
    
    @Test
    public void shouldBeMapToTagDto() {
        final List<TagDto> dto = mapper.mapToTagDto(createTagCollection());
        assertThat(dto, is(notNullValue()));
        assertThat(dto.size(), is(3));
        for (TagDto tag : dto) {
            if (tag.getName().equalsIgnoreCase("dessert")) {
                assertThat(tag.getQuantity(), is(3));
            }
            if (tag.getName().equalsIgnoreCase("vegetarisch")) {
                assertThat(tag.getQuantity(), is(2));
            }
            if (tag.getName().equalsIgnoreCase("vorspeise")) {
                assertThat(tag.getQuantity(), is(1));
            }
        }
    }
    
    @Test
    public void shouldBeMapToRecipe() {
        final Recipe recipe = mapper.maoToRecipe(recipeDto);
        assertThat(recipe, is(notNullValue()));
        assertThat(recipe.getUuid(), is(recipeDto.getId()));
        assertThat(recipe.getTitle(), is(recipeDto.getTitle()));
        assertThat(recipe.getPreample(), is(recipeDto.getPreample()));
        assertThat(recipe.getNoOfPerson(), is(recipeDto.getNoOfPeople()));
        assertThat(recipe.getImage(), is(notNullValue()));
        assertThat(recipe.getImage().getUrl(), is(recipeDto.getImage()));
        assertThat(recipe.getAddingDateTime(), is(recipeDto.getAddingDate()));
        assertThat(recipe.getLastUpdateTime(), is(recipeDto.getEditingDate()));
        assertThat(recipe.getRating(), is(notNullValue()));
        assertThat(recipe.getRating().intValue(), is(recipeDto.getRating()));
        assertThat(recipe.getTags(), is(notNullValue()));
        assertThat(recipe.getTags().size(), is(4));
        final Set<String> tags = recipe.getTags();
        assertThat(recipeDto.getTags().containsAll(tags), is(true));
        final List<Ingredient> ingredients = recipe.getIngredients();
        assertThat(ingredients, is(notNullValue()));
        assertThat(ingredients.size() ,is(3));
        for (int i = 0; i < 3; i++) {
            assertThat(ingredients.get(i).getQuantity(), is(recipeDto.getIngredients().get(i).getPortion()));
            assertThat(ingredients.get(i).getDescription(), is(recipeDto.getIngredients().get(i).getDescription()));
            assertThat(ingredients.get(i).getAnnotation(), is(recipeDto.getIngredients().get(i).getComment()));
        }
    }

    private List<String> createTagCollection() {
        final List<String> tags = new ArrayList<String>(6);
        tags.add("dessert");
        tags.add("dessert");
        tags.add("dessert");
        tags.add("vegetarisch");
        tags.add("vegetarisch");
        tags.add("vorspeise");
        return tags;
    }
}
