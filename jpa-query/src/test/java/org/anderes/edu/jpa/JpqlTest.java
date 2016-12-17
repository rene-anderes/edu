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

/**
 * Beispiele für JPQL
 * 
 * @author René Anderes
 *
 */
public class JpqlTest {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
       
    /**
     * Einfache Abfrage die alle Records einer Entity zurück liefert
     */
    @Test
    public void selectAllEntities() {
        
        final List<Book> books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        
        assertThat(books.size(), is(6));
    }
    
    /**
     * Einfache Abfrage aller Records mittel untypisierten Query
     */
    @Test
    public void nontypedQuery() {
        
        final List<?> books = entityManager.createQuery("SELECT b FROM Book b").getResultList();
        
        assertThat(books.size(), is(6));
    }
    
    /**
     * Einfach Abfrage in der eine WHERE verwendet wird
     */
    @Test
    public void simpleWhereClause() {
        
        final List<Book> books = entityManager.createQuery("SELECT b FROM Book b WHERE b.title LIKE '%Scrum%'", Book.class).getResultList();
        
        assertThat(books.size(), is(2));
    }
    
    /**
     * Einzelne Attribute einer Entity deklarieren
     */
    @Test
    @SuppressWarnings("unchecked")
    public void defineTheAttributes() {
        
        /* Einzelne Attribute werden selektiert */
        final List<Object[]> authorNames = entityManager.createQuery("SELECT a.firstName, a.lastName FROM Author a").getResultList();
        assertThat(authorNames.size(), is(2));
        
        /* Erzeugen eines neuen Pojo-Objekt pro Entity */
        final List<AuthorInfo> authorInfos = entityManager.createQuery(
                        "SELECT NEW org.anderes.edu.jpa.model.AuthorInfo(a.firstName, a.lastName) FROM Author a", AuthorInfo.class).getResultList();
        assertThat(authorInfos.size(), is(2));
    }
    
    /**
     * Impliziter und expliziter JOIN für Entities mit Beziehungen zueinander
     */
    @Test
    @SuppressWarnings("unchecked")
    public void joinRelatedEntities() {
        
        /* Impliziter JOIN */
        final List<Object[]> booksInfo = entityManager.createQuery("SELECT b.title, b.publisher.name FROM Book b").getResultList();
        assertThat(booksInfo.size(), is(6));
        
        /* Expliziter JOIN entspricht dem obigen Beispiel */
        final List<Object[]> books = entityManager.createQuery("SELECT b.title, p.name FROM Book b JOIN b.publisher p").getResultList();
        assertThat(books.size(), is(6));
    }
    
    /**
     * JOIN für Entities die keine Beziehung zueinander haben
     */
    @Test
    @SuppressWarnings("unchecked")
    public void joinUnrelatedEntities() {
        
        final List<Object[]> authors = entityManager.createQuery(
                        "SELECT a.firstName, a.lastName, p.phoneNumber FROM Author a JOIN PhoneBook p ON p.firstname = a.firstName AND p.lastname = a.lastName").getResultList();
        
        assertThat(authors.size(), is(1));
    }
    
    /**
     * JOIN für Entities mit Beziehungen und WHERE
     */
    @Test
    @SuppressWarnings("unchecked")
    public void joinedRelatedEntitiesWithSimpleWhereClause() {
        
        final List<Object[]> booksInfo = entityManager.createQuery(
                        "SELECT b.title, b.publisher.name FROM Book b WHERE b.publisher.name = 'Redline Verlag'").getResultList();
        
        assertThat(booksInfo.size(), is(2));
        
        /* Expliziter JOIN bei @ManyTo... notwendig */
        final List<Author> authors = entityManager.createQuery(
                        "SELECT a FROM Author a JOIN a.books b WHERE b.title like '%Scrum%'", Author.class).getResultList();
        
        assertThat(authors.size(), is(2));
        
        /* Mehrere JOIN's */
        final List<Object[]> infos = entityManager.createQuery(
                        "SELECT a.firstName, a.lastName FROM Author a JOIN a.books b JOIN b.publisher p WHERE b.title like '%Komplexithoden%' AND p.name like '%Redline%'")
                        .getResultList();
        
        assertThat(infos.size(), is(1));
    }
    
    /**
     * Ordnen des Query-Resultats mittels ORDER BY
     */
    @Test
    public void simpleOrderBy() {
    
        final List<Book> booksOrdered = entityManager.createQuery("SELECT b FROM Book b ORDER BY b.title ASC", Book.class).getResultList();
        
        assertThat(booksOrdered.size(), is(6));
        assertThat(booksOrdered.iterator().next().getTitle(), startsWith("Der agile Festpreis"));
    }
    
    /**
     * Gruppieren des Query-Resultats mittels GROUP BY
     * <p/>
     * Verwendung von JPQL Funktionen (hier count())
     */
    @Test
    @SuppressWarnings("unchecked")
    public void simpleGroupBy() {
        
        final List<Object[]> authorsInfo = entityManager.createQuery(
                        "SELECT a, count(b) FROM Author a JOIN a.books b GROUP BY a ORDER BY a.lastName").getResultList();
        
        assertThat(authorsInfo.size(), is(2));
        assertThat(((Author)authorsInfo.get(0)[0]).getLastName(), is("Gloger"));
        assertThat(authorsInfo.get(0)[1], is(4L));
        assertThat(((Author)authorsInfo.get(1)[0]).getLastName(), is("Pfläging"));
        assertThat(authorsInfo.get(1)[1], is(2L));

    }
    
    /**
     * Verwendung von Sub-Query im WHERE Abschnitt
     */
    @Test
    public void simpleSubQuery() {
        
        final List<Author> authors = entityManager.createQuery(
                        "SELECT a FROM Author a WHERE a.lastName IN (SELECT p.lastname FROM PhoneBook p WHERE p.firstname = 'Boris')", Author.class).getResultList();
        
        assertThat(authors.size(), is(1));
        assertThat(authors.iterator().next().getFirstName(), is("Boris"));
    }

    /**
     * Limitieren der Anzahl Records aus dem Query-Result
     * <p/>
     * Hier wird es als Paging verwendet
     */
    @Test
    public void limitNumberOfRecords() {
        
        final List<Book> books = entityManager
                        .createQuery("SELECT b FROM Book b ORDER BY b.title", Book.class)
                        .setFirstResult(2)
                        .setMaxResults(3)
                        .getResultList();
        
        assertThat(books.size(), is(3));
    }
    
    /**
     * Flexibilität durch Binding
     */
    @Test
    public void simpleBinding() {
        final String wantedTitle = "%Scrum%";
        
        final List<Book> books = entityManager
                        .createQuery("SELECT b FROM Book b WHERE b.title LIKE :title", Book.class)
                        .setParameter("title", wantedTitle)
                        .getResultList();
        
        assertThat(books.size(), is(2));
    }
    
    /**
     * Einzelnes Resultat aus der Query erwartet
     */
    @Test
    public void singleResultSample() {
        
        final Book book = entityManager
                        .createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                        .setParameter("isbn", "978-3868815863")
                        .getSingleResult();
        
        assertThat(book.getPublisher().getName(), is("Redline Verlag"));
    }
    
    /**
     * Eine NamedQuery die bei der Entity Author definiert wurde, wird hier eingesetzt
     * 
     * @see Author
     */
    @Test
    public void namedQueryFromAuthor() {
        
        final List<Author> authors = entityManager
                        .createNamedQuery("Author.All.OrderByLastname", Author.class)
                        .getResultList();
        
        assertThat(authors.size(), is(2));
    }
    
    /**
     * Die Named-Query befindet sich im orm.xml
     */
    @Test
    public void namedQueryFromOrmXml() {
        final List<Book> books =  entityManager
                        .createNamedQuery("Book.findByAuthor", Book.class)
                        .setParameter("firstname", "Boris")
                        .setParameter("lastname", "Gloger")
                        .getResultList();

        assertThat("Boris Gloger hat 4 Bücher", books.size(), is(4));
    }

    @BeforeClass
    public static void setUpOnce() {
        /* Der Name der Persistence-Unit entspricht dem in der Konfigurationsdatei META-INF/persistence.xml */
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
