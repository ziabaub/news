package com.epam.lab.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    T save(T entity);

    T update(T entity);

    List<T> saveAll(List<T> entity);

    List<T> findAll();

    Optional<T> findById(int id);

    void deleteById(int id);

    void deleteAll();

    T findBy (String s);
}
