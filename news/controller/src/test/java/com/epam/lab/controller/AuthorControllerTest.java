package com.epam.lab.controller;

import com.epam.lab.TestUtil;
import com.epam.lab.configuration.ControllerConfiguration;
import com.epam.lab.configuration.ServiceConfiguration;
import com.epam.lab.model.entities.Author;
import com.epam.lab.service.AuthorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        ServiceConfiguration.class,
        ControllerConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("hibernate")
public class AuthorControllerTest {

    private static final String AUTHOR = "/author/";
    private static final String NAME = "Christie";
    private static final String SURNAME = "Jacobs";
    private MockMvc mockMvc;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createShouldCheckEmptyCreateEntryShouldReturnValidationErrorStatusAndRollBack() throws Exception {
        Author author = new Author();

        mockMvc.perform(post(AUTHOR)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(author))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    public void createShouldCheckCreatedEntryShouldReturnValidationStatus() throws Exception {

        Author author = new Author();
        author.setName(NAME);
        author.setSurname(SURNAME);

        authorService.save(author);
        mockMvc.perform(post(AUTHOR)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(author))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(author.getId() + 1)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.surname", is(SURNAME)));
    }

    @Test
    public void readByIdTestShouldReturnAuthorById() throws Exception {
        Author author = new Author();
        author.setName(NAME);
        author.setSurname(SURNAME);
        authorService.save(author);

        mockMvc.perform(post(AUTHOR)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(author))
        ).andExpect(status().isCreated());

        mockMvc.perform(get(AUTHOR + "id/{id}", author.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(author.getId())))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.surname", is(SURNAME)));

    }


    @Test
    public void createAllShouldCheckCreatedEntryListShouldReturnValidationStatus() throws Exception {

        Author first = new Author();
        first.setName(NAME);
        first.setSurname(SURNAME);

        Author second = new Author();
        second.setName(NAME);
        second.setSurname(SURNAME);


        List<Author> requested = Arrays.asList(first, second);
        authorService.saveAll(requested);
        mockMvc.perform(post(AUTHOR + "all/")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(requested))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(first.getId() + 2)))
                .andExpect(jsonPath("$[0].name", is(NAME)))
                .andExpect(jsonPath("$[0].surname", is(SURNAME)))
                .andExpect(jsonPath("$[1].id", is(second.getId() + 2)))
                .andExpect(jsonPath("$[1].name", is(NAME)))
                .andExpect(jsonPath("$[1].surname", is(SURNAME)));

    }

}

