package org.anderes.edu.jdk9.features;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

public class FactoryMethodsForCollectionsTest {
    
    @Test
    public void shouldBecheckOldStyleList() {
        FactoryMethodsForCollections forCollections = new FactoryMethodsForCollections();
        final List<Point> list = forCollections.oldStyleList();
        assertThat(list.size(), is(4));
    }

    @Test
    public void shouldBecheckNewStyleList() {
        FactoryMethodsForCollections forCollections = new FactoryMethodsForCollections();
        final List<Point> list = forCollections.newStyleList();
        assertThat(list.size(), is(4));
    }
}
