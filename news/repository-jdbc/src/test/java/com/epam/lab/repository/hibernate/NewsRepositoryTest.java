package com.epam.lab.repository.hibernate;

import com.epam.lab.configuration.HibernateRepositoryConfiguration;
import com.epam.lab.model.entities.Author;
import com.epam.lab.model.entities.News;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateRepositoryConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("hibernate")
public class NewsRepositoryTest {

    private static final String FULL_TEXT = "Apple’s ‘Hungry Designers Wanted’ is an amazing idea. Making the the famous apple logo half eaten look like a person to represent the hungry job applicants is extremely playful. It presents a challenge to designers; Apple needs motivated individuals to work for them and they are encouraging them to send their CVs to the email provided.";
    private static final String FULL_TEXT_2 = "Alkemy decided to create a job post for a person that doesn’t exist. Their ad message says “if you think you are our imaginary person, send us your real CV’ which translates to if you believe you have the skills to work for us, go ahead and apply for the job. The image used for this advert has movie references, and this makes it stronger. Overall, it’s both clever and catchy just like a job ad should be.";
    private static final String FULL_TEXT_3 = "This ad has a powerful message from the Germany job-hunting website jobsintown.de that says you have to make sure to choose your job wisely to avoid ending up in the wrong one. Using a series of well-designed ads that depict people manually working ATMs, jukeboxes, coffee machines, instant photo booths and washing machines, it shows how miserable a bad job can make you. It’s a very imaginative way to encourage jobseekers to apply for the ‘right’ job.";
    private static final String AUTHOR_NAME = "ziad";
    private static final String SURNAME = "sarrih";
    private static final String TITLE = "Hungry Designers";
    private static final String SHORT_TEXT = "Apple’s ‘Hungry Designers Wanted’ is an amazing idea.";
    private static final String CRE_DATE = "1999-01-08";
    private static final String MOD_DATE = "1999-01-28";


    @Autowired
    private Repository<News> newsRepository;

    @Autowired
    private Repository<Author> authorRepository;


    @Test
    public void selectById() {

        Author expectedAuthor = new Author();
        News expectedNews = new News();
        expectedAuthor.setName(AUTHOR_NAME);
        expectedAuthor.setSurname(SURNAME);
        expectedNews.setTitle(TITLE);
        expectedNews.setShortText(SHORT_TEXT);
        expectedNews.setFullText(FULL_TEXT);
        expectedNews.setCreationDate(CRE_DATE);
        expectedAuthor.setId(1);
        expectedNews.setId(2);
        expectedNews.setAuthor(expectedAuthor);
        expectedNews.setModificationDate(MOD_DATE);
        expectedNews.setTags(Collections.emptyList());

        authorRepository.save(expectedAuthor);
        newsRepository.save(expectedNews);

        Optional<News> actually = newsRepository.findById(expectedNews.getId());
        Assert.assertTrue(actually.isPresent());
        Assert.assertEquals(expectedNews,actually.get());
    }

    @Test
    public void selectAll() {
        Author fAuthor = new Author();
        News fNews = new News();
        fAuthor.setName(AUTHOR_NAME);
        fAuthor.setSurname(SURNAME);
        fNews.setTitle(TITLE);
        fNews.setShortText(SHORT_TEXT);
        fNews.setFullText(FULL_TEXT);
        fNews.setCreationDate(CRE_DATE);
        fNews.setAuthor(fAuthor);
        fNews.setModificationDate(MOD_DATE);
        fNews.setTags(Collections.emptyList());
        authorRepository.save(fAuthor);

        Author sAuthor = new Author();
        News sNews = new News();
        sAuthor.setName("Teresa");
        sAuthor.setSurname("Silva");
        sNews.setTitle("Imaginary Person?");
        sNews.setShortText("Alkemy decided to create a job post for ...");
        sNews.setFullText(FULL_TEXT_2);
        sNews.setCreationDate("1998-02-08");
        sNews.setAuthor(sAuthor);
        sNews.setModificationDate("1998-02-28");
        sNews.setTags(Collections.emptyList());
        authorRepository.save(sAuthor);

        Author tAuthor = new Author();
        News tNews = new News();
        tAuthor.setName("Christie");
        tAuthor.setSurname("Jacobs");
        tNews.setTitle("Life’s Too Short");
        tNews.setShortText("Apple’s ‘This ad has a powerful  ...");
        tNews.setFullText(FULL_TEXT_3);
        tNews.setCreationDate("2000-05-08");
        tNews.setAuthor(tAuthor);
        tNews.setModificationDate("2000-05-28");
        tNews.setTags(Collections.emptyList());
        authorRepository.save(tAuthor);

        List<News> expected = Arrays.asList(fNews, sNews, tNews);
        newsRepository.saveAll(expected);
        List<News> actually = newsRepository.findAll();
        Assert.assertTrue(actually.containsAll(expected));
    }

    @Test
    public void update() {
        Author author = new Author();
        News news = new News();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        news.setTitle(TITLE);
        news.setShortText(SHORT_TEXT);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        author.setId(1);
        news.setId(2);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.emptyList());

        newsRepository.save(news);

        author.setName(AUTHOR_NAME + "test");
        author.setSurname(SURNAME + "test");
        news.setTitle(TITLE + "test");
        news.setShortText(SHORT_TEXT + "test");
        news.setFullText(FULL_TEXT + "test");
        news.setCreationDate(CRE_DATE);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.emptyList());

        newsRepository.update(news);
        Optional<News> actually = newsRepository.findById(news.getId());
        Assert.assertTrue(actually.isPresent());
        Assert.assertEquals(news, actually.get());

    }

}