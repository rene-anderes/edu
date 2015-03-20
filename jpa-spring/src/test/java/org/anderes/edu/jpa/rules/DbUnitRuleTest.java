package org.anderes.edu.jpa.rules;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

public class DbUnitRuleTest {

    @Test
    public void shouldBeOneTableAndOneColumn() {
        DbUnitRule rule = new DbUnitRule();
        String[] excludeColumns = { "RECIPE.UUID"};
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        
        assertThat(map.containsKey("RECIPE"), is(true));
        assertThat(map.get("RECIPE").length, is(1));
        assertThat(map.get("RECIPE")[0], is("UUID"));
    }

    @Test
    public void shouldBeTwoTableAndTwoColumn() {
        DbUnitRule rule = new DbUnitRule();
        String[] excludeColumns = { "RECIPE.UUID", "RECIPE.DESCRIPTION", "TAGS.ID", "TAGS.DESCRIPTION"};
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        
        assertThat(map.containsKey("RECIPE"), is(true));
        assertThat(map.get("RECIPE").length, is(2));
        assertThat(map.get("RECIPE")[0], is("UUID"));
        assertThat(map.get("RECIPE")[1], is("DESCRIPTION"));
        assertThat(map.containsKey("TAGS"), is(true));
        assertThat(map.get("TAGS").length, is(2));
        assertThat(map.get("TAGS")[0], is("ID"));
        assertThat(map.get("TAGS")[1], is("DESCRIPTION"));
    }
    
    @Test
    public void shouldBeTwoTableAndTwoColumnCaseInsensitive() {
        DbUnitRule rule = new DbUnitRule();
        String[] excludeColumns = { "RECIPE.uuid", "recipe.DESCRIPTION", "tags.ID", "TAGS.DESCRIPTION"};
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        
        assertThat(map.containsKey("RECIPE"), is(true));
        assertThat(map.get("RECIPE").length, is(2));
        assertThat(map.get("RECIPE")[0], is("UUID"));
        assertThat(map.get("RECIPE")[1], is("DESCRIPTION"));
        assertThat(map.containsKey("TAGS"), is(true));
        assertThat(map.get("TAGS").length, is(2));
        assertThat(map.get("TAGS")[0], is("ID"));
        assertThat(map.get("TAGS")[1], is("DESCRIPTION"));
    }
    
    @Test
    public void ShouldBeEmptyMap() {
        DbUnitRule rule = new DbUnitRule();
        String[] excludeColumns = { "" };
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        assertThat(map.size(), is(0));
    }
    
    @Test
    public void ShouldBeEmptyMapByWrongData() {
        DbUnitRule rule = new DbUnitRule();
        String[] excludeColumns = { "UUID" };
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        assertThat(map.size(), is(0));
    }
}
