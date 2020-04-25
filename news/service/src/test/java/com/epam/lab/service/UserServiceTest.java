package com.epam.lab.service;

import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Roles;
import com.epam.lab.model.entities.User;
import com.epam.lab.repository.hibernate.repository.impl.RoleRepository;
import com.epam.lab.repository.hibernate.repository.impl.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private static final String NAME = "Howard";
    private static final String SURNAME = "Buchanan";
    private static final String LOGIN = "Howard";
    private static final String PASS = "sdfgsdfgsdf";
    private static final String ROLE = "admin";
    private static final int ID = 1;
    private UserService userService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    private ArgumentCaptor<Roles> roleCaptor;

    @Captor
    private ArgumentCaptor<List<User>> userListCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userService = new UserService(userRepository, roleRepository);
    }

//    @Test
//    public void addItemTestShouldCheckInsertingIntoDatabase() throws ServiceException {
//        User expected = new User();
//        Roles roles = new Roles();
//        roles.setRole(ROLE);
//        roles.setId(ID);
//        expected.setName(NAME);
//        expected.setSurname(SURNAME);
//        expected.setLogin(LOGIN);
//        expected.setPassword(PASS);
//        expected.setId(ID);
//        expected.setRoles(roles);
//
//        userService.save(expected);
//        verify(userRepository, times(1)).save(userCaptor.capture());
//        verify(roleRepository, times(1)).save(roleCaptor.capture());
//        verifyNoMoreInteractions(userRepository);
//
//    }
//
//    @Test
//    public void saveAll() throws ServiceException {
//        User first = new User();
//        Roles role = new Roles();
//        role.setRole(ROLE);
//        role.setId(role.hashCode());
//        first.setName(NAME);
//        first.setSurname(SURNAME);
//        first.setLogin(LOGIN);
//        first.setPassword(PASS);
//        first.setId(ID);
//        first.setRoles(role);
//
//        User second = new User();
//        Roles role2 = new Roles();
//        role2.setRole(ROLE);
//        role2.setId(role2.hashCode());
//        second.setName(NAME);
//        second.setSurname(SURNAME);
//        second.setLogin(LOGIN);
//        second.setPassword(PASS);
//        second.setId(ID);
//        second.setRoles(role2);
//
//        List<User> users = Arrays.asList(first, second);
//
//        userService.saveAll(users);
//        verify(userRepository, times(1)).save(userListCaptor.capture());
//        verify(roleRepository, times(2)).save(roleCaptor.capture());
//        verifyNoMoreInteractions(userRepository);
//    }

    @Test
    public void getByIdItemsTestShouldCheckSelectingFromDatabaseById() throws ServiceException {
        User expected = new User();
        Roles role = new Roles();
        role.setRole(ROLE);
        role.setId(ID);
        expected.setName(NAME);
        expected.setSurname(SURNAME);
        expected.setLogin(LOGIN);
        expected.setPassword(PASS);
        expected.setId(ID);
        expected.setRoles(role);
        when(userRepository.findById(ID)).thenReturn(Optional.of(expected));
        Optional<User> actual = userService.getById(ID);
        verify(userRepository, times(1)).findById(ID);
        verifyNoMoreInteractions(userRepository);

        actual.ifPresent(a -> Assert.assertEquals(a, expected));
    }

    @Test
    public void getAllItemsTestShouldCheckSelectingFromDatabase() throws ServiceException {
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        List<User> actual = userService.getAll();

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);

        Assert.assertEquals(actual, users);
    }

}

















