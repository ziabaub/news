package com.epam.lab.repository.hibernate;

import com.epam.lab.configuration.HibernateRepositoryConfiguration;
import com.epam.lab.model.entities.Author;
import com.epam.lab.model.entities.News;
import com.epam.lab.repository.Repository;
import com.epam.lab.repository.hibernate.repository.impl.NewsRepository;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateRepositoryConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("hibernate")
public class NamedQueryIntegrationNewsTest {

    private static final String FULL_TEXT = "Apple’s ‘Hungry Designers Wanted’ is an amazing idea. Making the the famous apple logo half eaten look like a person to represent the hungry job applicants is extremely playful. It presents a challenge to designers; Apple needs motivated individuals to work for them and they are encouraging them to send their CVs to the email provided.";
    private static final String AUTHOR_NAME = "ziad";
    private static final String SURNAME = "sarrih";
    private static final String TITLE = "Hungry Designers";
    private static final String SHORT_TEXT = "Apple’s ‘Hungry Designers Wanted’ is an amazing idea.";
    private static final String CRE_DATE = "1999-01-08";
    private static final String MOD_DATE = "1999-01-28";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private Repository<Author> authorRepository;

    @Test
    @Transactional
    public void getByAuthorTestSelectingNewsEntityByAuthor() {
        Author author = new Author();
        News expected = new News();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        expected.setTitle(TITLE);
        expected.setShortText(SHORT_TEXT);
        expected.setFullText(FULL_TEXT);
        expected.setCreationDate(CRE_DATE);
        author.setId(1);
        expected.setId(2);
        expected.setAuthor(author);
        expected.setModificationDate(MOD_DATE);
        expected.setTags(Collections.emptyList());

        authorRepository.save(author);
        newsRepository.save(expected);

        News actually = sessionFactory.getCurrentSession().createNamedQuery("News.findByAuthor",News.class)
                .setParameter("author",author)
                .getSingleResult();

        Assert.assertEquals(expected,actually);

    }


}