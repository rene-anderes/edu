package org.anderes.edu.jpa.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.anderes.edu.jpa.domain.Recipe;
import org.anderes.edu.jpa.domain.RecipeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:unittest-application-context.xml")
public class MapperTest {

    @Inject
    private Mapper mapper;
    private Recipe recipe;
    
    @Before
    public void setUp() throws Exception {
        recipe = RecipeBuilder.buildRecipe();
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
