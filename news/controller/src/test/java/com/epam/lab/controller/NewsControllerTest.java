package com.epam.lab.controller;

import com.epam.lab.TestUtil;
import com.epam.lab.configuration.ControllerConfiguration;
import com.epam.lab.configuration.ServiceConfiguration;
import com.epam.lab.model.entities.Author;
import com.epam.lab.model.entities.News;
import com.epam.lab.model.entities.Tag;
import com.epam.lab.service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        ServiceConfiguration.class,
        ControllerConfiguration.class})
@ActiveProfiles("hibernate")
public class NewsControllerTest {

    private static final String NEWS = "/news/";
    private static final String FULL_TEXT = "Apple’s ‘Hungry Designers Wanted’ is an amazing idea. Making the the famous apple logo half eaten look like a person to represent the hungry job applicants is extremely playful. It presents a challenge to designers; Apple needs motivated individuals to work for them and they are encouraging them to send their CVs to the email provided.";
    private static final String AUTHOR_NAME = "ziad ";
    private static final String SURNAME = "sarrih";
    private static final String TAGS_NAME = "1980 Olympics";
    private static final String TITLE = "Hungry Designers";
    private static final String SHORT_Text = "Apple’s ‘Hungry Designers Wanted’ is an amazing idea. ";
    private static final String CRE_DATE = "1999-01-08";
    private static final String MOD_DATE = "1999-01-28";
    private static final int ID = -1611719075;

    private MockMvc mockMvc;

    @Autowired
    private NewsService newsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock newsects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test(expected = NestedServletException.class)
    public void createShouldCheckEmptyCreateEntryShouldReturnValidationErrorStatus() throws Exception {
        News news = new News();

        mockMvc.perform(post(NEWS)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(news))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(newsService);
    }


    @Test
    public void createShouldCheckCreatedEntryShouldReturnValidationStatus() throws Exception {

        Author author = new Author();
        News news = new News();
        Tag tag = new Tag();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        tag.setName(TAGS_NAME);
        news.setTitle(TITLE);
        news.setShortText(SHORT_Text);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.singletonList(tag));

        newsService.save(news);

        mockMvc.perform(post(NEWS)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(news))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.author.name", is(author.getName())))
                .andExpect(jsonPath("$.author.surname", is(author.getSurname())))
                .andExpect(jsonPath("$.title", is(news.getTitle())))
                .andExpect(jsonPath("$.shortText", is(news.getShortText())))
                .andExpect(jsonPath("$.fullText", is(news.getFullText())))
                .andExpect(jsonPath("$.creationDate", is(news.getCreationDate())))
                .andExpect(jsonPath("$.modificationDate", is(news.getModificationDate())))
                .andExpect(jsonPath("$.tags[0].name", is(tag.getName())));
    }

    @Test
    public void readByIdTestShouldReturnAuthorById() throws Exception {
        Author author = new Author();
        News news = new News();
        Tag tag = new Tag();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        tag.setName(TAGS_NAME);
        news.setTitle(TITLE);
        news.setShortText(SHORT_Text);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.singletonList(tag));
        newsService.save(news);

        mockMvc.perform(get(NEWS + "id/{id}", news.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(news.getId())))
                .andExpect(jsonPath("$.title", is(news.getTitle())))
                .andExpect(jsonPath("$.author.id", is(author.getId())))
                .andExpect(jsonPath("$.author.name", is(author.getName())))
                .andExpect(jsonPath("$.author.surname", is(author.getSurname())))
                .andExpect(jsonPath("$.tags[0].id", is(tag.getId())))
                .andExpect(jsonPath("$.tags[0].name", is(tag.getName())))
                .andExpect(jsonPath("$.shortText", is(news.getShortText())))
                .andExpect(jsonPath("$.fullText", is(news.getFullText())))
                .andExpect(jsonPath("$.creationDate", is(news.getCreationDate())))
                .andExpect(jsonPath("$.modificationDate", is(news.getModificationDate())));

    }

    @Test
    public void update() throws Exception {
        Author author = new Author();
        News news = new News();
        Tag tag = new Tag();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        tag.setName(TAGS_NAME);
        news.setTitle(TITLE);
        news.setShortText(SHORT_Text);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.singletonList(tag));

        newsService.save(news);
        news.setFullText("test");
        news.setShortText("test");
        news.setTitle("test");
        author.setName("test");
        tag.setName("test");

        newsService.update(news);

        mockMvc.perform(put(NEWS)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(news))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.author.id", is(author.getId())))
                .andExpect(jsonPath("$.author.name", is(author.getName())))
                .andExpect(jsonPath("$.author.surname", is(SURNAME)))
                .andExpect(jsonPath("$.id", is(news.getId())))
                .andExpect(jsonPath("$.title", is(news.getTitle())))
                .andExpect(jsonPath("$.shortText", is(news.getShortText())))
                .andExpect(jsonPath("$.fullText", is(news.getFullText())))
                .andExpect(jsonPath("$.creationDate", is(CRE_DATE)))
                .andExpect(jsonPath("$.modificationDate", is(MOD_DATE)))
                .andExpect(jsonPath("$.tags[0].id", is(tag.getId())))
                .andExpect(jsonPath("$.tags[0].name", is(tag.getName())));

    }


    @Test
    public void deleteById() throws Exception {
        Author author = new Author();
        News news = new News();
        Tag tag = new Tag();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        author.setId(author.hashCode());
        tag.setName(TAGS_NAME);
        news.setTitle(TITLE);
        news.setShortText(SHORT_Text);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.singletonList(tag));

        newsService.save(news);
        mockMvc.perform(delete(NEWS + "{id}", news.getId()))
                .andExpect(status().isOk());
    }


}

