package com.epam.lab.service;


import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Roles;
import com.epam.lab.model.entities.User;
import com.epam.lab.model.security.UserPrincipal;
import com.epam.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService implements UserDetailsService {


    private final Repository<User> repository;
    private final Repository<Roles> roleRepository;

    @Autowired
    public UserService(Repository<User> repository, Repository<Roles> roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public Optional<User> getById(int id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("no user under id [ " + id + " ] !", e);
        }
    }

    public List<User> getAll() throws ServiceException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("no user list available !", e);
        }
    }

    public User save(User entity) throws ServiceException {
        try {
            Roles roles = roleRepository.save(entity.getRoles());
            entity.setRoles(roles);
            entity.setId(roles.getId());
            return repository.save(entity);
        } catch (DataAccessException e) {
            String m = e.getCause().getCause().getMessage();
            throw new ServiceException(m.substring(m.indexOf('\n') + 1).trim(), e);
        }
    }

    public List<User> saveAll(List<User> entity) throws ServiceException {
        try {
            entity.forEach(e -> roleRepository.save(e.getRoles()));
            return repository.saveAll(entity);
        } catch (DataAccessException e) {
            String m = e.getCause().getCause().getMessage();
            throw new ServiceException(m.substring(m.indexOf('\n') + 1).trim(), e);
        }
    }

    public void deleteById(int id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("delete user under id [ " + id + " ] not allowed  !", e);
        }
    }

    public User update(User entity) throws ServiceException {
        try {
            roleRepository.update(entity.getRoles());
            return repository.update(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("user not updated !!", e);
        }
    }

    public void deleteAll() throws ServiceException {
        try {
            repository.deleteAll();
        } catch (DataAccessException e) {
            throw new ServiceException("user not updated !!", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = repository.findBy(login);
        return new UserPrincipal(user);

    }
}