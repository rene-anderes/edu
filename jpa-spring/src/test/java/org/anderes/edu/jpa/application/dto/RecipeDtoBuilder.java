package org.anderes.edu.jpa.application.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecipeDtoBuilder {

    public static RecipeDto buildRecipeDto() {
        final RecipeDto dto = new RecipeDto();
        dto.setAddingDate(december(31, 2000));
        dto.setEditingDate(december(31, 2014));
        dto.setIngredients(buildIngredientDto());
        dto.setNoOfPeople("4");
        dto.setPreample("For a new software system, the requirements will not be completely known until after the users have used it.");
        dto.setPreparation("Uncertainty is inherent and inevitable in software development processes and products.");
        dto.setRating(4);
        dto.setTitle("Softwareentwicklung");
        dto.setTags(buildTags());
        dto.setImage("http://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Ferrari_Werke.JPG/800px-Ferrari_Werke.JPG");
        return dto;
    }

    private static List<String> buildTags() {
        final List<String> tags = new ArrayList<String>(4);
        tags.add("Scrum");
        tags.add("Agile");
        tags.add("Methode");
        tags.add("Parktiken");
        return tags;
    }

    private static List<IngredientDto> buildIngredientDto() {
        final IngredientDto ingredient_1 = new IngredientDto();
        ingredient_1.setPortion("5");
        ingredient_1.setDescription("Developer");
        ingredient_1.setComment("nicht zu viele");
        final IngredientDto ingredient_2 = new IngredientDto();
        ingredient_2.setPortion("1");
        ingredient_2.setDescription("Product Owner");
        ingredient_2.setComment("entsprechende Kompetenz vorhanden");
        final IngredientDto ingredient_3 = new IngredientDto();
        ingredient_3.setPortion("1");
        ingredient_3.setDescription("Scrum Master");
        ingredient_3.setComment("keine Doppelrollen");
        final List<IngredientDto> dto = new ArrayList<IngredientDto>(3);
        dto.add(ingredient_1);
        dto.add(ingredient_2);
        dto.add(ingredient_3);
        return dto;
    }

    private static long december(int day, int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.DECEMBER, day);
        return calendar.getTimeInMillis();
    }

}
