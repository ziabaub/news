package com.epam.lab.controller;

import com.epam.lab.exception.MissingHeaderInfoException;
import com.epam.lab.exception.NotAllowedDeletionException;
import com.epam.lab.exception.RecordNotFoundException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.dto.TagDTO;
import com.epam.lab.model.entities.Tag;
import com.epam.lab.service.TagService;
import com.epam.lab.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.lab.statics.ConstantHolder.TAG;


@RestController
@RequestMapping(value = TAG, produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody TagDTO tagDTO) {
        try {
            Tag tag = ObjectMapperUtils.map(tagDTO, Tag.class);
            Tag res = service.save(tag);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PostMapping(value = "all/")
    public ResponseEntity<List<Tag>> createAll(@RequestBody List<TagDTO> tagsDTOS) {
        try {
            List<Tag> tags = ObjectMapperUtils.mapAll(tagsDTOS, Tag.class);
            List<Tag> res = service.saveAll(tags);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Tag> update(@RequestBody TagDTO tagDTO) {
        try {
            Tag tag = ObjectMapperUtils.map(tagDTO, Tag.class);
            Tag res = service.update(tag);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Tag>> readAll() {
        try {
            List<Tag> res = service.getAll();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Tag> deleteAll() {
        try {
            service.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new NotAllowedDeletionException(e.getMessage());
        }

    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<Tag> read(@PathVariable("id") int id) {
        try {
            Tag tag = service.getById(id).orElseThrow(
                    () -> new RecordNotFoundException("no records"));
            return new ResponseEntity<>(tag, HttpStatus.OK);
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