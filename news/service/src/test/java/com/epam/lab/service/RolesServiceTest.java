package com.epam.lab.service;

import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Roles;
import com.epam.lab.repository.hibernate.repository.impl.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RolesServiceTest {

    private static final String ROLE = "admin";
    private static final int ID = 1;
    private RoleService roleService;
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void addItemTestShouldCheckInsertingIntoDatabase() throws ServiceException {
        Roles role = new Roles();
        role.setId(ID);
        role.setRole(ROLE);
        roleService.save(role);
        ArgumentCaptor<Roles> toDoArgument = ArgumentCaptor.forClass(Roles.class);
        verify(roleRepository).save(toDoArgument.capture());
        verifyNoMoreInteractions(roleRepository);

    }

    @Test
    public void saveAll() throws  ServiceException {
        Roles first = new Roles();
        first.setId(ID);
        first.setRole(ROLE);

        Roles second = new Roles();
        second.setId(ID);
        second.setRole(ROLE);
        List<Roles> roles = Arrays.asList(first, second);

        roleService.saveAll(roles);
        verify(roleRepository).saveAll(roles);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    public void getByIdItemsTestShouldCheckSelectingFromDatabaseById() throws  ServiceException {
        Roles expected = new Roles();
        expected.setId(ID);
        expected.setRole(ROLE);

        when(roleRepository.findById(ID)).thenReturn(Optional.of(expected));

        Optional<Roles> actual = roleService.getById(ID);

        verify(roleRepository).findById(ID);
        verifyNoMoreInteractions(roleRepository);

        actual.ifPresent(a-> Assert.assertEquals(a, expected));
    }

    @Test
    public void getAllItemsTestShouldCheckSelectingFromDatabase() throws  ServiceException {
        List<Roles> roles = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(roles);

        List<Roles> actual = roleService.getAll();

        verify(roleRepository).findAll();
        verifyNoMoreInteractions(roleRepository);

        Assert.assertEquals(actual, roles);
    }

}