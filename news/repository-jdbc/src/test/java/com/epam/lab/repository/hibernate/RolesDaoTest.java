package com.epam.lab.repository.hibernate;

import com.epam.lab.configuration.HibernateRepositoryConfiguration;
import com.epam.lab.model.entities.Roles;
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
public class RolesDaoTest {

    private static final String ROLE = "admin";

    @Autowired
    private Repository<Roles> roleRepository;

    @Test
    public void selectById() {
        Roles roles = new Roles();
        roles.setRole(ROLE);

        roleRepository.save(roles);

        Optional<Roles> actually = roleRepository.findById(roles.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a-> Assert.assertEquals(a,roles));
    }

    @Test
    public void selectAll() {
        Roles first = new Roles();
        first.setRole(ROLE);

        Roles third = new Roles();
        third.setRole("journalist");

        List<Roles> expected = Arrays.asList(first,third);
        roleRepository.saveAll(expected);

        List<Roles> actually = roleRepository.findAll();
        Assert.assertTrue(actually.containsAll(expected));
    }

    @Test
    public void update() {
        Roles roles = new Roles();
        roles.setRole(ROLE);
        roleRepository.save(roles);

        roles.setRole(ROLE + "test");

        roleRepository.update(roles);

        Optional<Roles> actually = roleRepository.findById(roles.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a-> Assert.assertEquals(a,roles));
    }

}