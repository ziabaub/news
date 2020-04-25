package com.epam.lab.controller;

import com.epam.lab.exception.MissingHeaderInfoException;
import com.epam.lab.exception.NotAllowedDeletionException;
import com.epam.lab.exception.RecordNotFoundException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.dto.UserDTO;
import com.epam.lab.model.entities.User;
import com.epam.lab.service.UserService;
import com.epam.lab.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.lab.statics.ConstantHolder.USER;


@RestController
@RequestMapping(value = USER, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO userDTO) {
        try {
            User user = ObjectMapperUtils.map(userDTO, User.class);
            User res = service.save(user);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PostMapping(value = "all/")
    public ResponseEntity<Iterable<User>> createAll(@RequestBody List<UserDTO> usersDTOS) {
        try {
            List<User> users = ObjectMapperUtils.mapAll(usersDTOS, User.class);
            Iterable<User> res = service.saveAll(users);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody UserDTO userDTO) {
        try {
            User user = ObjectMapperUtils.map(userDTO, User.class);
            User res = service.update(user);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> readAll() {
        try {
            Iterable<User> res = service.getAll();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<User> deleteAll() {
        try {
            service.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new NotAllowedDeletionException(e.getMessage());
        }

    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<User> read(@PathVariable("id") int id) {
        try {
            User user = service.getById(id).orElseThrow(
                    () -> new RecordNotFoundException("no records"));
            return new ResponseEntity<>(user, HttpStatus.OK);
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
