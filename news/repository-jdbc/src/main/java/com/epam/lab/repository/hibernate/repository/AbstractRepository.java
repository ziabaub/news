package com.epam.lab.repository.hibernate.repository;

import com.epam.lab.repository.Repository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository<T> implements Repository<T> {
    protected SessionFactory sessionFactory;

    @Override
    public T findBy(String s) {
        return null;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
