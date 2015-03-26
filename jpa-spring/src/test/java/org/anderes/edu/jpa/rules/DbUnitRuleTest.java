package org.anderes.edu.jpa.rules;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.junit.Before;
import org.junit.Test;

public class DbUnitRuleTest {

    private DbUnitRule rule;
    
    @Before
    public void setup() {
        rule = new DbUnitRule();
    }
    
    @Test
    public void shouldBeOneTableAndOneColumn() {
        String[] excludeColumns = { "RECIPE.UUID"};
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        
        assertThat(map.containsKey("RECIPE"), is(true));
        assertThat(map.get("RECIPE").length, is(1));
        assertThat(map.get("RECIPE")[0], is("UUID"));
    }

    @Test
    public void shouldBeTwoTableAndTwoColumn() {
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
        String[] excludeColumns = { "" };
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        assertThat(map.size(), is(0));
    }
    
    @Test
    public void ShouldBeEmptyMapByWrongData() {
        String[] excludeColumns = { "UUID" };
        Map<String, String[]> map = rule.buildMapFromStringArray(excludeColumns);
        assertThat(map.size(), is(0));
    }
    
    @Test
    public void shouldBeBuildDataSet() throws DataSetException {
        final String[] dataSetFiles = { "/dbUnit/forDbUnitRuleTest.xls" };
        CompositeDataSet dataset = rule.buildDataSet(dataSetFiles);
        
        assertThat(dataset, is(notNullValue()));
        assertThat(dataset.getTableNames().length, is(4));
    }
    
    @Test
    public void shouldBeBuildDataSetWithTwoFiles() throws DataSetException {
        final String[] dataSetFiles = { "/dbUnit/forDbUnitRuleTest.xls", "/dbUnit/forDbUnitRuleTest.xml" };
        CompositeDataSet dataset = rule.buildDataSet(dataSetFiles);
        
        assertThat(dataset, is(notNullValue()));
        assertThat(dataset.getTableNames().length, is(7));
    }
}
