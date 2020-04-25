package com.epam.lab.controller;

import com.epam.lab.TestUtil;
import com.epam.lab.configuration.ControllerConfiguration;
import com.epam.lab.configuration.ServiceConfiguration;
import com.epam.lab.model.entities.Roles;
import com.epam.lab.model.entities.User;
import com.epam.lab.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

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
public class UserControllerTest {

    private static final String NAME = "Howard";
    private static final String SURNAME = "Buchanan";
    private static final String LOGIN = "Howard";
    private static final String PASS = "sdfgsdfgsdf";
    private static final String ROLE = "admin";
    private static final String USER = "/user/";
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

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
        User user = new User();
        Roles role = new Roles();
        role.setRole(ROLE);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setLogin(LOGIN);
        user.setPassword(PASS);
        user.setRoles(role);
        userService.save(user);
        mockMvc.perform(post(USER)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isCreated());

        mockMvc.perform(get(USER + "id/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.surname", is(SURNAME)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.roles.id", is(role.getId())))
                .andExpect(jsonPath("$.roles.role", is(ROLE)));

    }

    @Test(expected = NestedServletException.class)
    public void createShouldCheckEmptyCreateEntryShouldReturnValidationErrorStatus() throws Exception {
        User user = new User();

        mockMvc.perform(post(USER)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }

    @Test
    public void createShouldCheckCreatedEntryShouldReturnValidationStatus() throws Exception {

        Roles role = new Roles();
        role.setRole(ROLE);
        User user = new User();
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setLogin(LOGIN);
        user.setPassword(PASS);
        user.setRoles(role);
        userService.save(user);
        mockMvc.perform(post(USER)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(user.getId() + 1)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.surname", is(SURNAME)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.roles.id", is(role.getId() + 1)))
                .andExpect(jsonPath("$.roles.role", is(ROLE)));
    }

    @Test
    public void createAllShouldCheckCreatedEntryListShouldReturnValidationStatus() throws Exception {

        Roles firstRole = new Roles();
        firstRole.setRole(ROLE);
        User first = new User();
        first.setName(NAME);
        first.setSurname(SURNAME);
        first.setLogin(LOGIN);
        first.setPassword(PASS);
        first.setRoles(firstRole);

        Roles secondRole = new Roles();
        secondRole.setRole(ROLE);
        User second = new User();
        second.setName(NAME);
        second.setSurname(SURNAME);
        second.setLogin(LOGIN);
        second.setPassword(PASS);
        second.setRoles(secondRole);

        List<User> added = Arrays.asList(first, second);
        userService.saveAll(added);
        mockMvc.perform(post(USER + "all/")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(first.getId() + 2)))
                .andExpect(jsonPath("$[0].name", is(NAME)))
                .andExpect(jsonPath("$[0].surname", is(SURNAME)))
                .andExpect(jsonPath("$[0].login", is(LOGIN)))
                .andExpect(jsonPath("$[0].roles.id", is(first.getId() + 2)))
                .andExpect(jsonPath("$[0].roles.role", is(ROLE)))
                .andExpect(jsonPath("$[1].id", is(second.getId() + 2)))
                .andExpect(jsonPath("$[1].name", is(NAME)))
                .andExpect(jsonPath("$[1].surname", is(SURNAME)))
                .andExpect(jsonPath("$[1].login", is(LOGIN)))
                .andExpect(jsonPath("$[1].roles.id", is(second.getId() + 2)))
                .andExpect(jsonPath("$[1].roles.role", is(ROLE)));

    }


}

