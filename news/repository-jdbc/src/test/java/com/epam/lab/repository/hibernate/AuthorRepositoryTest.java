package com.epam.lab.repository.hibernate;

import com.epam.lab.configuration.HibernateRepositoryConfiguration;
import com.epam.lab.model.entities.Author;
import com.epam.lab.repository.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateRepositoryConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("hibernate")
public class AuthorRepositoryTest {

    private static final String NAME = "Christie";
    private static final String SURNAME = "Jacobs";

    @Autowired
    private Repository<Author> authorRepository;

    @Test
    public void selectById() {
        Author expected = new Author();
        expected.setName(NAME);
        expected.setSurname(SURNAME);
        authorRepository.save(expected);
        Optional<Author> actually = authorRepository.findById(expected.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a -> Assert.assertEquals(a, expected));
    }

    @Test
    public void selectAll() {
        Author first = new Author();
        first.setName(NAME);
        first.setSurname(SURNAME);

        Author second = new Author();
        second.setName("ziad");
        second.setSurname("sarrih");

        Author third = new Author();
        third.setName("Teresa");
        third.setSurname("Silva");

        List<Author> expected = Arrays.asList(first, second, third);
        authorRepository.saveAll(expected);
        List<Author> actually = authorRepository.findAll();
        Assert.assertTrue(actually.containsAll(expected));
    }

    @Test
    public void update() {
        Author author = new Author();
        author.setName(NAME);
        author.setSurname(SURNAME);
        authorRepository.save(author);

        author.setName(NAME + "test");
        author.setSurname(SURNAME + "test");

        authorRepository.update(author);
        Optional<Author> actually = authorRepository.findById(author.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a -> Assert.assertEquals(a, author));
    }

}