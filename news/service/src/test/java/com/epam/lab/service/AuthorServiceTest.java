package com.epam.lab.service;

import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Author;
import com.epam.lab.repository.hibernate.repository.impl.AuthorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    private static final String NAME = "Christie";
    private static final String SURNAME = "Jacobs";
    private static final int ID = 1;
    private AuthorService authorService;
    private AuthorRepository authorRepository;

    @Before
    public void setUp() {
        authorRepository = mock(AuthorRepository.class);
        authorService = new AuthorService(authorRepository);
    }

    @Test
    public void addItemTestShouldCheckInsertingIntoDatabase() throws ServiceException {
        Author author = new Author();
        author.setName(NAME);
        author.setSurname(SURNAME);
        authorService.save(author);
        ArgumentCaptor<Author> toDoArgument = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository, times(1)).save(toDoArgument.capture());
        verifyNoMoreInteractions(authorRepository);

    }

    @Test
    public void saveAll() throws ServiceException {
        Author first = new Author();
        first.setName(NAME);
        first.setSurname(SURNAME);

        Author second = new Author();
        second.setName(NAME);
        second.setSurname(SURNAME);
        List<Author> authors = Arrays.asList(first, second);

        authorService.saveAll(authors);
        verify(authorRepository, times(1)).saveAll(authors);
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    public void getByIdItemsTestShouldCheckSelectingFromDatabaseById() throws ServiceException {
        Author expected = new Author();
        expected.setId(ID);
        expected.setName(NAME);
        expected.setSurname(SURNAME);
        when(authorRepository.findById(ID)).thenReturn(Optional.of(expected));

        Optional<Author> actual = authorService.getById(ID);

        verify(authorRepository, times(1)).findById(ID);
        verifyNoMoreInteractions(authorRepository);

        actual.ifPresent(a -> Assert.assertEquals(a, expected));
    }

    @Test
    public void getAllItemsTestShouldCheckSelectingFromDatabase() throws ServiceException {
        List<Author> authors = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> actual = authorService.getAll();

        verify(authorRepository, times(1)).findAll();
        verifyNoMoreInteractions(authorRepository);

        Assert.assertEquals(actual, authors);
    }

}