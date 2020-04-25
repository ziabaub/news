package com.epam.lab.controller;

import com.epam.lab.TestUtil;
import com.epam.lab.configuration.ControllerConfiguration;
import com.epam.lab.configuration.ServiceConfiguration;
import com.epam.lab.model.entities.Tag;
import com.epam.lab.service.TagService;
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
public class TagControllerTest {

    private static final String NAME = "1-800-GOT-JUNK";
    private static final String TAG = "/tag/";
    private static final int ID = 1;
    private MockMvc mockMvc;

    @Autowired
    private TagService tagService;

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
    public void readByIdTestShouldReturnAuthorById() throws Exception {
        Tag tag = new Tag();
        tag.setName(NAME);

        tagService.save(tag);
        mockMvc.perform(post(TAG)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tag))
        )
                .andExpect(status().isCreated());

        mockMvc.perform(get(TAG + "id/{id}", tag.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(tag.getId())))
                .andExpect(jsonPath("$.name", is(NAME)));
    }

    @Test
    public void createShouldCheckEmptyCreateEntryShouldReturnValidationErrorStatus() throws Exception {
        Tag tag = new Tag();

        mockMvc.perform(post(TAG)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tag))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    public void createShouldCheckCreatedEntryShouldReturnValidationStatus() throws Exception {

        Tag tag = new Tag();
        tag.setName(NAME);
        tagService.save(tag);

        mockMvc.perform(post(TAG)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tag))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(tag.getId() + 1)))
                .andExpect(jsonPath("$.name", is(NAME)));
    }


}

