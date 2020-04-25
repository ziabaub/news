package com.epam.lab.controller;

import com.epam.lab.exception.MissingHeaderInfoException;
import com.epam.lab.exception.NotAllowedDeletionException;
import com.epam.lab.exception.RecordNotFoundException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.dto.RolesDTO;
import com.epam.lab.model.entities.Roles;
import com.epam.lab.service.RoleService;
import com.epam.lab.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.lab.statics.ConstantHolder.ROLE;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = ROLE, produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Roles> create(@RequestBody RolesDTO rolesDTO) {
        try {
            Roles role = ObjectMapperUtils.map(rolesDTO, Roles.class);
            Roles res = service.save(role);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PostMapping(value = "all/")
    public ResponseEntity<Iterable<Roles>> createAll(@RequestBody List<Roles> rolesDTOS) {
        try {
            List<Roles> roles = ObjectMapperUtils.mapAll(rolesDTOS, Roles.class);
            Iterable<Roles> res = service.saveAll(roles);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new MissingHeaderInfoException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Roles> update(@RequestBody RolesDTO rolesDTO) {
        try {
            Roles role = ObjectMapperUtils.map(rolesDTO, Roles.class);
            Roles res = service.update(role);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Roles>> readAll() {
        try {
            Iterable<Roles> res = service.getAll();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ServiceException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Roles> deleteAll() {
        try {
            service.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new NotAllowedDeletionException(e.getMessage());
        }

    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<Roles> read(@PathVariable("id") int id) {
        try {
            Roles role = service.getById(id).orElseThrow(
                    () -> new RecordNotFoundException("no records"));
            return new ResponseEntity<>(role, HttpStatus.OK);
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