package org.anderes.edu.callby;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CallByReference {

    private List<String> orderPositions = new ArrayList<>();
    private Order order = new Order();


    @Test
    public void shouldBeCheck() {
        order.setOrders(orderPositions);
        
        orderPositions.clear();
        orderPositions = getNewCollection();
        
        assertThat(orderPositions.size(), is(order.getOrders().size()));
    }
    
    @Test
    public void shouldBeCheckAsWell() {
        order.setOrders(orderPositions);
        
        orderPositions.clear();
        orderPositions.addAll(getNewCollection());
        
        assertThat(orderPositions.size(), is(order.getOrders().size()));
    }

    private List<String> getNewCollection() {
        List<String> newCollection = new ArrayList<String>(2);
        newCollection.add("Text 1");
        newCollection.add("Text 2");
        return newCollection;
    }
    
    private static class Order {
        
        private List<String> orderCollection = new ArrayList<>();
        
        void setOrders(List<String> orders) {
            orderCollection = orders;
        }
        
        List<String> getOrders() {
            return orderCollection;
        }
    }
}
