package com.epam.lab.controller;

import com.epam.lab.exception.MissingHeaderInfoException;
import com.epam.lab.exception.NotAllowedDeletionException;
import com.epam.lab.exception.RecordNotFoundException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.dto.AuthorDTO;
import com.epam.lab.model.entities.Author;
import com.epam.lab.service.AuthorService;
import com.epam.lab.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.lab.statics.ConstantHolder.AUTHOR;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = AUTHOR, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService service;

    @Autowired
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody AuthorDTO authorDTO) {
        try {
            Author author = ObjectMapperUtils.map(authorDTO, Author.class);
            Author res = service.save(author);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PostMapping(value = "all/")
    public ResponseEntity<Iterable<Author>> createAll(@RequestBody List<AuthorDTO> authorDTOS) {
        try {
            List<Author> authors = ObjectMapperUtils.mapAll(authorDTOS, Author.class);
            Iterable<Author> res = service.saveAll(authors);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Author> update(@RequestBody AuthorDTO authorDTO) {
        try {
            Author author = ObjectMapperUtils.map(authorDTO, Author.class);
            Author res = service.update(author);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Author>> readAll() {
        try {
            Iterable<Author> res = service.getAll();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Author> deleteAll() {
        try {
            service.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new NotAllowedDeletionException(e.getMessage());
        }

    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<Author> read(@PathVariable("id") int id) {
        try {
            Author author = service.getById(id).orElseThrow(
                    () -> new RecordNotFoundException("no records"));
            return new ResponseEntity<>(author, HttpStatus.OK);
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
}