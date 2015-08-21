package org.anderes.edu.relations.onetomany;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderItemRelationPersistenceTest {

    private EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testDB");
    
    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
    }
    
    @Test
    public void shouldBeFindOneOrder() {
        final Order order = entityManager.find(Order.class, 5001L);
        
        assertThat(order, is(notNullValue()));
        assertThat(order.getOrderItems().size(), is(2));
        assertThat(order.getOrderItems().iterator().next().getOrder(), is(order));
    }
    
    /**
     * Die Entity Order besitzt für die @OneToMany Beziehung ein {@code cascade = CascadeType.ALL}.
     * Dadurch werden allfällig vorhandene neu OrdetItems ebenfalls gespeichert.
     * </p>
     * Funktioniert mit {@code merge()} und {@code persist()} 
     */
    @Test
    public void shouldBePersistNewOrderWithNewOrderItems() {
        final Order order = new Order(9999L);
        final OrderItem orderItem_1 = new OrderItem("Gartenschere");
        final OrderItem orderItem_2 = new OrderItem("Gartenschlauch");
        order.addOrderItem(orderItem_1);
        order.addOrderItem(orderItem_2);
        
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        
        assertThat(order.getOrderItems().size(), is(2));
    }
    
    /**
     * Der Versuch ein neues OrderItem inkl. Order zu speichern
     * muss fehl schlagen, da kein cascade angegeben ist.
     * </p>
     * In diesem Fall macht es auch aus fachlicher Sicht keinen Sinn
     * mittels OrderItem die neue Bestellung abzuspeichern.
     */
    @Test(expected = RollbackException.class)
    public void shouldNotBePersistNewOrderItemWithNewOrder() {
        final Order order = new Order(7777L);
        final OrderItem orderItem = new OrderItem("Gartenschere");
        order.addOrderItem(orderItem);
        
        entityManager.getTransaction().begin();
        entityManager.persist(orderItem);
        entityManager.getTransaction().commit();
    }
    
    /**
     * Eine neue Bestellposition (OrderItem) einer bestehenden
     * Bestellung (Order) hinzufügen und die Bestellung persistieren.
     */
    @Test
    public void shouldBeAddOneNewOrderItem() {
        final Order order = entityManager.find(Order.class, 5002L);
        final OrderItem orderItem = new OrderItem("Heckenschere");
        order.addOrderItem(orderItem);
        
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        
        assertThat(order.getOrderItems().size(), is(3));
        assertThat(orderItem.getOrder(), is(order));
    }
    
    @Test
    public void shouldBeDeleteOneOrderItem() {
        
    }
}
