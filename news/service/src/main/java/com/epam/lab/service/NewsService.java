package com.epam.lab.service;


import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Author;
import com.epam.lab.model.entities.News;
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
public class NewsService {

    private final Repository<News> repository;
    private final Repository<Author>  authorRepository;
    private final Repository<Tag>  tagRepository;

    @Autowired
    public NewsService(Repository<News> repository, Repository<Author> authorRepository, Repository<Tag> tagRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    public Optional<News> getById(int id) throws ServiceException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("no news under id [ " + id + " ] !", e);
        }
    }

    public List<News> getAll() throws ServiceException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("no news list available !", e);
        }
    }

    public News save(News entity) throws ServiceException {
        try {
            Author author = authorRepository.save(entity.getAuthor());
            List<Tag> tags = entity.getTags();
            tagRepository.saveAll(tags);
            entity.setAuthor(author);
            entity.setTags(tags);
            return repository.save(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the news try later !", e);
        }
    }

    public List<News> saveAll(List<News> entity) throws ServiceException {
        try {
            for (News n : entity) {
                Author author = authorRepository.save(n.getAuthor());
                List<Tag> tags = tagRepository.saveAll(n.getTags());
                n.setAuthor(author);
                n.setTags(tags);
            }
            return repository.saveAll(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("can't save the news try later !", e);
        }
    }

    public void deleteById(int id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("delete news under id [ " + id + " ] not allowed  !", e);
        }
    }

    public News update(News entity) throws ServiceException {
        try {
            List<Tag> tags = entity.getTags();
            tags.forEach(tagRepository::update);
            Author author = entity.getAuthor();
            if (author != null) {
                authorRepository.update(author);
            }
            return repository.update(entity);
        } catch (DataAccessException e) {
            throw new ServiceException("news not updated !!", e);
        }
    }

    public void deleteAll() throws ServiceException {
        try {
            repository.deleteAll();
        } catch (DataAccessException e) {
            throw new ServiceException("news not updated !!", e);
        }
    }

    public Object getCount() throws ServiceException {
        try {
            return 20 ;//repository.count();
        } catch (DataAccessException e) {
            throw new ServiceException("news list is empty !!", e);
        }
    }
}