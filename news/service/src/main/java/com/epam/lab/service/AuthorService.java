package com.epam.lab.service;


import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Author;
import com.epam.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AuthorService {

    private final Repository<Author> repository;

    @Autowired
    public AuthorService(Repository<Author> repository) {
        this.repository = repository;
    }

    public Optional<Author> getById(int id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("no author under id [ " + id + " ] !", e);
        }
    }

    public List<Author> getAll() throws ServiceException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("no author list available !", e);
        }
    }

    public Author save(Author entity) throws ServiceException {
        try {
            return repository.save(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the author try later !", e);
        }
    }

    public List<Author> saveAll(List<Author> entity) throws ServiceException {
        try {
            return repository.saveAll(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the author try later !", e);
        }
    }

    public void deleteById(int id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("delete author under id [ " + id + " ]  not allowed !", e);
        }
    }

    public Author update(Author entity) throws ServiceException {
        try {
            return repository.update(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("author not updated !!", e);
        }
    }

    public void deleteAll() throws ServiceException {
        try {
            repository.deleteAll();
        } catch (DataAccessException e) {
            throw new ServiceException("author not updated !!", e);
        }
    }
}
