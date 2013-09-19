package org.anderes.edu.builder;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.anderes.edu.builder.Product;
import org.junit.Test;

public class ProductTest {

    @Test
    public void constructorWithIdAndPrice() {
	BigDecimal price = BigDecimal.valueOf(36.5);
        Product.Builder builder = new Product.Builder("050-0047", price);
        assertNotNull(builder);
        Product p = builder.build();
        assertNotNull(p);
        assertEquals("050-0047", p.getId());
        assertEquals(BigDecimal.valueOf(36.5), p.getPrice());
    }

    @Test
    public void constructorWithIdPriceAndDescription() {
	BigDecimal price = BigDecimal.valueOf(36.5);
        Product.Builder builder = new Product.Builder("050-0047", price);
        assertNotNull(builder);
        Product p = builder.salesDescription("Neue Verpackung").build();
        assertNotNull(p);
        assertEquals("050-0047", p.getId());
        assertEquals(BigDecimal.valueOf(36.5), p.getPrice());
        assertEquals("Neue Verpackung", p.getSalesDescription());
    }
}
