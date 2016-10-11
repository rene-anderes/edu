package org.anderes.edu.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.anderes.edu.jpa.entity.Author;
import org.anderes.edu.jpa.entity.Book;
import org.anderes.edu.jpa.model.AuthorInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JpqlTest {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
       
    /**
     * Select all entities
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#Select_query_examples">Select Queries</a>
     */
    @Test
    public void selectAllEntities() {
        final List<Book> books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        assertThat(books.size(), is(3));
    }
    
    /**
     * Use WHERE clause
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#Select_query_examples">Select Queries</a>
     */
    @Test
    public void simpleWhereClause() {
        final List<Book> books = entityManager.createQuery("SELECT b FROM Book b WHERE b.title LIKE '%Scrum%'", Book.class).getResultList();
        assertThat(books.size(), is(1));
    }
    
    /**
     * Define the attributes you want to select
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#Constructors">SELECT Clause with Constructors</a>
     */
    @Test
    @SuppressWarnings("unchecked")
    public void defineTheAttributes() {
        
        /* Einzelne Attribute werden selektiert */
        final List<Object[]> authorNames = entityManager.createQuery("SELECT a.firstName, a.lastName FROM Author a").getResultList();
        assertThat(authorNames.size(), is(2));
        
        /* Pojo mit den gewünschten Daten erstellen */
        final List<AuthorInfo> authorInfos = entityManager.createQuery(
                        "SELECT NEW org.anderes.edu.jpa.model.AuthorInfo(a.firstName, a.lastName) FROM Author a", AuthorInfo.class).getResultList();
        assertThat(authorInfos.size(), is(2));
    }
    
    /**
     * Join related entities in the FROM clause
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#FROM_Clause">FROM Clause</a>
     */
    @Test
    @SuppressWarnings("unchecked")
    public void joinRelatedEntities() {
        
        /* Impliziter JOIN */
        final List<Object[]> booksInfo = entityManager.createQuery("SELECT b.title, b.publisher.name FROM Book b").getResultList();
        assertThat(booksInfo.size(), is(3));
        
        /* Expliziter JOIN entspricht dem obigen Beispiel */
        final List<Object[]> books = entityManager.createQuery("SELECT b.title, p.name FROM Book b JOIN b.publisher p").getResultList();
        assertThat(books.size(), is(3));
    }
    
    /**
     * Join unrelated entities in the FROM clause
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#FROM_Clause">FROM clause</a>
     */
    @Test
    @SuppressWarnings("unchecked")
    public void joinUnrelatedEntities() {
        
        final List<Object[]> authors = entityManager.createQuery(
                        "SELECT a.firstName, a.lastName, p.phoneNumber FROM Author a JOIN PhoneBook p ON p.firstname = a.firstName AND p.lastname = a.lastName").getResultList();
        
        assertThat(authors.size(), is(1));
    }
    
    /**
     * Join related entities with WHERE clause
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#WHERE_Clause">WHERE clause</a>
     */
    @Test
    @SuppressWarnings("unchecked")
    public void joinedRelatedEntitiesWithSimpleWhereClause() {
        
        final List<Object[]> booksInfo = entityManager.createQuery(
                        "SELECT b.title, b.publisher.name FROM Book b WHERE b.publisher.name = 'Redline Verlag'").getResultList();
        
        assertThat(booksInfo.size(), is(1));
        
        /* Expliziter JOIN bei @ManyTo... notwendig */
        final List<Author> authors = entityManager.createQuery(
                        "SELECT a FROM Author a JOIN a.books b WHERE b.title like '%Scrum%'", Author.class).getResultList();
        
        assertThat(authors.size(), is(1));
    }
    
    /**
     * Order your query resuls with ORDER BY
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#ORDER_BY_clause">ORDER BY clause</a>
     */
    @Test
    public void simpleOrderBy() {
    
        final List<Book> booksOrdered = entityManager.createQuery("SELECT b FROM Book b ORDER BY b.title", Book.class).getResultList();
        
        assertThat(booksOrdered.size(), is(3));
        assertThat(booksOrdered.iterator().next().getTitle(), startsWith("Komplexithoden"));
    }
    
    /**
     * Group your query results with GROUP BY
     * <p/>
     * JPQL also supports a small <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#JPQL_supported_functions">set of standard functions</a>
     * that you can use in your queries.
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#GROUP_BY_Clause">GROUP BY clause</a>
     */
    @Test
    @SuppressWarnings("unchecked")
    public void simpleGroupBy() {
        
        final List<Object[]> authorsInfo = entityManager.createQuery(
                        "SELECT a, count(b) FROM Author a JOIN a.books b GROUP BY a").getResultList();
        
        assertThat(authorsInfo.size(), is(2));
        assertThat(((Author)authorsInfo.get(0)[0]).getLastName(), is("Glogger"));
        assertThat(authorsInfo.get(0)[1], is(2L));
        assertThat(((Author)authorsInfo.get(1)[0]).getLastName(), is("Pfläging"));
        assertThat(authorsInfo.get(1)[1], is(1L));

    }
    
    /**
     * Use subqueries in the WHERE clause
     * 
     * @see <a href="https://en.wikibooks.org/wiki/Java_Persistence/JPQL#Sub-selects_in_FROM_clause">Sub-selects in FROM clause</a>
     */
    @Test
    public void simpleSubQuery() {
        final List<Author> authors = entityManager.createQuery(
                        "SELECT a FROM Author a WHERE a.lastName IN (SELECT p.lastname FROM PhoneBook p WHERE p.firstname = 'Boris')", Author.class).getResultList();
        
        assertThat(authors.size(), is(1));
        assertThat(authors.iterator().next().getFirstName(), is("Boris"));
    }
    
    /**
     * Limit the number of records in your result set
     */
    @Test
    public void limitNumberOfRecords() {
        final List<Book> books = entityManager
                        .createQuery("SELECT b FROM Book b", Book.class)
                        .setMaxResults(2)
                        .getResultList();
        
        assertThat(books.size(), is(2));
    }

    @BeforeClass
    public static void setUpOnce() {
        // Der Name der Persistence-Unit entspricht der in der Konfigurationsdatei META-INF/persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
    }
    
    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
        entityManagerFactory.close();
    }
}
