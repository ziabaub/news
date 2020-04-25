package com.epam.lab.service;


import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Roles;
import com.epam.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class RoleService {

    private final Repository<Roles> repository;

    @Autowired
    public RoleService(Repository<Roles> repository) {
        this.repository = repository;
    }

    public Optional<Roles> getById(int id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("no role under id [ " + id + " ] !", e);
        }
    }

    public List<Roles> getAll() throws ServiceException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("no role list available !", e);
        }
    }

    public Roles save(Roles entity) throws ServiceException {
        try {
            return repository.save(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the role try later !", e);
        }
    }

    public List<Roles> saveAll(List<Roles> entity) throws ServiceException {
        try {
            return repository.saveAll(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the roles try later !", e);
        }
    }

    public void deleteById(int id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("delete role under id [ " + id + " ] not allowed  !", e);
        }
    }

    public Roles update(Roles entity) throws ServiceException {
        try {
            return repository.update(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("role not updated !!", e);
        }
    }

    public void deleteAll() throws ServiceException {
        try {
            repository.deleteAll();
        } catch (DataAccessException e) {
            throw new ServiceException("role not updated !!", e);
        }
    }
}
