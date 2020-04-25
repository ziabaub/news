package com.epam.lab.controller;

import com.epam.lab.exception.MissingHeaderInfoException;
import com.epam.lab.exception.NotAllowedDeletionException;
import com.epam.lab.exception.RecordNotFoundException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.dto.NewsDTO;
import com.epam.lab.model.entities.News;
import com.epam.lab.service.NewsService;
import com.epam.lab.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.lab.statics.ConstantHolder.NEWS;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = NEWS, produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsService service;

    @Autowired
    public NewsController(NewsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<News> create(@RequestBody NewsDTO newsDTO) {
        try {
            News news = ObjectMapperUtils.map(newsDTO, News.class);
            News res = service.save(news);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PostMapping(value = "all/")
    public ResponseEntity<Iterable<News>> createAll(@RequestBody List<NewsDTO> newsDTOS) {
        try {
            List<News> news = ObjectMapperUtils.mapAll(newsDTOS, News.class);
            Iterable<News> res = service.saveAll(news);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<News> update(@RequestBody NewsDTO newsDTO) {
        try {
            News news = ObjectMapperUtils.map(newsDTO, News.class);
            News res = service.update(news);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<News>> readAll() {
        try {
            Iterable<News> res = service.getAll();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<News> deleteAll() {
        try {
            service.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new NotAllowedDeletionException(e.getMessage());
        }

    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<News> read(@PathVariable("id") int id) {
        try {
            News news = service.getById(id).orElseThrow(
                    () -> new RecordNotFoundException("no records"));
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new NotAllowedDeletionException(e.getMessage());
        }
    }

    @GetMapping(value = "count/")
    public ResponseEntity<Object> count() {
        try {
            Object count = service.getCount();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new NotAllowedDeletionException(e.getMessage());
        }
    }
}