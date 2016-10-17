package org.anderes.edu.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.anderes.edu.jpa.entity.Book;
import org.anderes.edu.jpa.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;

public class CompareJpqlAndCriteriaQuery {

    private BookRepository repository;
    
    @Before
    public void setUp() throws Exception {
        repository = BookRepository.createNewInstance();
    }
    
    @Test
    public void bookRepositoryJpqlTitleOnly() {

        final List<Book> books = repository.getBooksByJpql("Scrum", Optional.empty());
        
        assertThat(books, is(not(nullValue())));
        assertThat(books.size(), is(2));
    }
    
    @Test
    public void bookRepositoryJpqlTitleOrDescription() {

        final List<Book> books = repository.getBooksByJpql("Scrum", Optional.of("agile"));
        
        assertThat(books, is(not(nullValue())));
        assertThat(books.size(), is(2));
    }
    
    @Test
    public void bookRepositoryCriteriaQueryTitleOnly() {

        final List<Book> books = repository.getBooksByCriteria("Scrum", Optional.empty());
        
        assertThat(books, is(not(nullValue())));
        assertThat(books.size(), is(2));
    }
    
    @Test
    public void bookRepositoryCriteriaQueryTitleOrDescription() {

        final List<Book> books = repository.getBooksByCriteria("Scrum", Optional.of("agile"));
        
        assertThat(books, is(not(nullValue())));
        assertThat(books.size(), is(2));
    }
}
