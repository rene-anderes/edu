package org.anderes.edu.relations.onetomany;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class OrderItemRelationTest {

    /**
     * Der gegenseitige Update der bidirectionl Beziehung wird hier überprüft
     */
    @Test
    public void shouldBeCheckBirectionalOrder() {
        final Order order = new Order(100L);
        final OrderItem orderItem_1 = new OrderItem("Gartenschere");
        final OrderItem orderItem_2 = new OrderItem("Gartenschlauch");
        
        order.addOrderItem(orderItem_1);
        
        assertThat(order.getOrderItems(), is(notNullValue()));
        assertThat(order.getOrderItems().size(), is(1));
        assertThat(order.getOrderItems().iterator().next(), is(orderItem_1));
        assertThat(orderItem_1.getOrder(), is(notNullValue()));
        assertThat(orderItem_1.getOrder(), is(order));
        
        order.addOrderItem(orderItem_2);
        
        assertThat(order.getOrderItems().size(), is(2));
        assertThat(orderItem_2.getOrder(), is(notNullValue()));
        assertThat(orderItem_2.getOrder(), is(order));
        
        order.removeOrderItem(orderItem_1);
        assertThat(order.getOrderItems().size(), is(1));
        assertThat(order.getOrderItems().iterator().next(), is(orderItem_2));
        assertThat(orderItem_2.getOrder(), is(notNullValue()));
        assertThat(orderItem_2.getOrder(), is(order));
        assertThat(orderItem_1.getOrder(), is(nullValue()));
    }
    
    /**
     * Der gegenseitige Update der bidirectionl Beziehung wird hier überprüft
     */
    @Test
    public void shouldBeCheckBirectionalOrderItem() {
        final Order order_1 = new Order(100L);
        final Order order_2 = new Order(200L);
        final OrderItem orderItem_1 = new OrderItem("Gartenschere");
        final OrderItem orderItem_2 = new OrderItem("Gartenschlauch");
        order_1.addOrderItem(orderItem_1);
        order_1.addOrderItem(orderItem_2);
        
        orderItem_1.setOrder(null);
        
        assertThat(orderItem_1.getOrder(), is(nullValue()));
        assertThat(order_1.getOrderItems().size(), is(1));
        
        orderItem_2.setOrder(order_2);
        assertThat(orderItem_2.getOrder(), is(order_2));
        assertThat(order_1.getOrderItems().size(), is(0));
        assertThat(order_2.getOrderItems().size(), is(1));
    }
    
    /**
     * Wird eine Bestellung (Order) gelöscht ({@code EntityManager.remove()}), 
     * müssen auch die Bestellpositionen gelöscht werden.<br>
     * Die Methode {@code preRemove} wird mittels {@code @PreRemove}
     * annotiert und wird im JPA_Umfeld automatsich dafür sorgen das die Methode aufgerufen wird.
     */
    @Test
    public void shouldBeCheckOrderPreRemove() {
        final Order order = new Order(100L);
        final OrderItem orderItem_1 = new OrderItem("Gartenschere");
        final OrderItem orderItem_2 = new OrderItem("Gartenschlauch");
        order.addOrderItem(orderItem_1);
        order.addOrderItem(orderItem_2);
        
        order.preRemove();
        
        assertThat(order.getOrderItems().size(), is(0));
        assertThat(orderItem_1.getOrder(), is(nullValue()));
        assertThat(orderItem_2.getOrder(), is(nullValue()));
    }
    
    /**
     * Wird eine Bestellposition (OrderItem) gelöscht ({@code EntityManager.remove()}), 
     * muss auch die Beziehung zur Order aktualisiert werden.<br>
     * Die Methode {@code preRemove} wird mittels {@code @PreRemove}
     * annotiert und wird im JPA_Umfeld automatsich dafür sorgen das die Methode aufgerufen wird.
     */
    @Test
    public void shouldBeCheckOrderItemPreRemove() {
        final Order order = new Order(100L);
        final OrderItem orderItem_1 = new OrderItem("Gartenschere");
        final OrderItem orderItem_2 = new OrderItem("Gartenschlauch");
        order.addOrderItem(orderItem_1);
        order.addOrderItem(orderItem_2);
        
        orderItem_1.preRemove();
        
        assertThat(orderItem_1.getOrder(), is(nullValue()));
        assertThat(order.getOrderItems().size(), is(1));
        assertThat(order.getOrderItems().iterator().next(), is(orderItem_2));
    }
}
