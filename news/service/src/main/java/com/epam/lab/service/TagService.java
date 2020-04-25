package com.epam.lab.service;


import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Tag;
import com.epam.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TagService {

    private final Repository<Tag> repository;

    @Autowired
    public TagService(Repository<Tag> repository) {
        this.repository = repository;
    }

    public Optional<Tag> getById(int id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("no tag under id [ " + id + " ] !", e);
        }
    }

    public List<Tag> getAll() throws ServiceException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("no tag list available !", e);
        }
    }

    public Tag save(Tag entity) throws ServiceException {
        try {
            return repository.save(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the tag try later !", e);
        }
    }

    public List<Tag> saveAll(List<Tag> entity) throws ServiceException {
        try {
            return repository.saveAll(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the tag try later !", e);
        }
    }

    public void deleteById(int id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("delete tag under id [ " + id + " ] not allowed  !", e);
        }
    }

    public Tag update(Tag entity) throws ServiceException {
        try {
            return repository.update(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("tag not updated !!", e);
        }
    }

    public void deleteAll() throws ServiceException {
        try {
            repository.deleteAll();
        } catch (DataAccessException e) {
            throw new ServiceException("tag not updated !!", e);
        }
    }

}