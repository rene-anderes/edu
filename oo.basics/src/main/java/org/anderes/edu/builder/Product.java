package org.anderes.edu.builder;

import java.math.BigDecimal;

/**
 * Ein beliebiges Produkt
 */
public class Product {
    private final String id;
    private final BigDecimal price;
    private final String salesDescription;
    private final Product baseProduct;
    private final boolean approved;

    /**
     * Privater Konstruktor, kann nur vom Builder benutzt werden
     * 
     * @param builder
     *            Erbauer
     */
    private Product(Builder builder) {
        this.id = builder.id;
        this.price = builder.price;
        this.salesDescription = builder.salesDescription;
        this.baseProduct = builder.baseProduct;
        this.approved = builder.approved;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSalesDescription() {
        return salesDescription;
    }

    public Product getBaseProduct() {
        return baseProduct;
    }

    public boolean isApproved() {
        return approved;
    }

    /**
     * Erbauer für die Instanzierung eines Product-Objekt
     */
    public static class Builder {
        private final String id;
        private final BigDecimal price;
        private String salesDescription = "";
        private Product baseProduct = null;
        private boolean approved = true;

        public Builder(final String id, final BigDecimal price) {
            this.id = id;
            this.price = price;
        }

        public Builder salesDescription(final String salesDescription) {
            this.salesDescription = salesDescription;
            return this;
        }

        public Builder baseProduct(final Product baseProduct) {
            this.baseProduct = baseProduct;
            return this;
        }

        public Builder approved(final boolean approved) {
            this.approved = approved;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}