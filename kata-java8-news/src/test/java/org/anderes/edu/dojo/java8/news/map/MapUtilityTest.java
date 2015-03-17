package org.anderes.edu.dojo.java8.news.map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class MapUtilityTest {

    @Test
    @Ignore
    public void shouldBeOneTableAndOneColumn() {
        String[] values = { "RECIPE.UUID"};
        Map<String, String[]> map = MapUtility.buildMapFromStringArray(values);
        
        assertThat(map.containsKey("RECIPE"), is(true));
        assertThat(map.get("RECIPE").length, is(1));
        assertThat(map.get("RECIPE")[0], is("UUID"));
    }

    @Test
    public void shouldBeTwoTableAndTwoColumn() {
        // given
        String[] values = { "RECIPE.UUID", "RECIPE.DESCRIPTION", "TAGS.ID", "TAGS.COMMENT"};
        
        // when
        Map<String, String[]> map = MapUtility.buildMapFromStringArray(values);
        
        // then
        assertThat(map.containsKey("RECIPE"), is(true));
        assertThat(map.get("RECIPE").length, is(2));
        assertThat(map.get("RECIPE")[0], is("UUID"));
        assertThat(map.get("RECIPE")[1], is("DESCRIPTION"));
        assertThat(map.containsKey("TAGS"), is(true));
        assertThat(map.get("TAGS").length, is(2));
        assertThat(map.get("TAGS")[0], is("ID"));
        assertThat(map.get("TAGS")[1], is("COMMENT"));
    }
    
    @Test
    public void ShouldBeEmptyMap() {
        // given
        String[] values = { "" };
        
        // when
        Map<String, String[]> map = MapUtility.buildMapFromStringArray(values);
        
        // then
        assertThat(map.size(), is(0));
    }
    
    @Test
    public void ShouldBeEmptyMapByWrongData() {
        // given
        String[] values = { "UUID" };
        
        // when
        Map<String, String[]> map = MapUtility.buildMapFromStringArray(values);
        
        // then
        assertThat(map.size(), is(0));
    }

}
