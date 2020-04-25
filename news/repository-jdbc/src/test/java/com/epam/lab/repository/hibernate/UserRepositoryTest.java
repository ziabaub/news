package com.epam.lab.repository.hibernate;

import com.epam.lab.configuration.HibernateRepositoryConfiguration;
import com.epam.lab.model.entities.Roles;
import com.epam.lab.model.entities.User;
import com.epam.lab.repository.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateRepositoryConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("hibernate")
public class UserRepositoryTest {

    private static final String NAME = "Ale";
    private static final String SURNAME = "Balloushi";
    private static final String LOGIN = "Bisho";
    private static final String PASS = "sdfgsdgsdf";
    private static final String ROLE = "admin";

    @Autowired
    private Repository<User> userRepository;

    @Autowired
    private Repository<Roles> roleRepository;

    @Test(expected = PersistenceException.class)
    public void selectById() {
        User user = new User();
        Roles role = new Roles();
        role.setRole(ROLE);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setLogin(LOGIN);
        user.setPassword(PASS);
        user.setRoles(role);

        roleRepository.save(role);
        userRepository.save(user);

        Optional<User> actually = userRepository.findById(user.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a-> Assert.assertEquals(a,user));
    }

    @Test(expected = PersistenceException.class)
    public void selectAll() {
        User first = new User();
        Roles role = new Roles();
        role.setRole(ROLE);
        first.setName(NAME);
        first.setSurname(SURNAME);
        first.setLogin("test");
        first.setPassword(PASS);
        first.setRoles(role);

        roleRepository.save(role);

        User second = new User();
        Roles role1 = new Roles();
        role1.setRole("journalist");
        second.setName("Nina");
        second.setSurname("Pope");
        second.setLogin("test1");
        second.setPassword("fgsdfg");
        second.setRoles(role1);
        roleRepository.save(role1);

        List<User> expected = Arrays.asList(first, second);
        userRepository.saveAll(expected);
        List<User> actually = userRepository.findAll();
        Assert.assertTrue(actually.containsAll(expected));
    }

    @Test(expected = PersistenceException.class)
    public void update() {
        User user = new User();
        Roles role = new Roles();
        role.setRole(ROLE);
        user.setName(NAME );
        user.setSurname(SURNAME);
        user.setLogin(LOGIN );
        user.setPassword(PASS);
        user.setRoles(role);

        userRepository.save(user);

        role.setRole(ROLE + "test");
        user.setName(NAME + "test");
        user.setSurname(SURNAME + "test");
        user.setLogin(LOGIN + "test");
        user.setPassword(PASS + "test");

        userRepository.update(user);

        Optional<User> actually = userRepository.findById(user.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a-> Assert.assertEquals(a,user));
    }

}
