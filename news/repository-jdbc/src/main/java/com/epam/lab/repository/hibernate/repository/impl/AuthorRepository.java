package com.epam.lab.repository.hibernate.repository.impl;

import com.epam.lab.model.entities.Author;
import com.epam.lab.repository.hibernate.repository.AbstractRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
@Profile(value = "hibernate")
public class AuthorRepository extends AbstractRepository<Author> {


    public Author save(Author entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity;
    }

    public Author update(Author entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    public List<Author> saveAll(List<Author> entity) {
        entity.forEach(a -> sessionFactory.getCurrentSession().save(a));
        return entity;
    }

    public List<Author> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);
        cq.select(root);
        Query<Author> query = session.createQuery(cq);
        return query.getResultList();
    }

    public Optional<Author> findById(int id) {
        Author author = sessionFactory.getCurrentSession().get(Author.class, id);
        return Optional.of(author);
    }

    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.byId(Author.class).load(id);
        session.delete(author);
    }

    public void deleteAll() {
        String q = "TRUNCATE author RESTART IDENTITY CASCADE";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(q);
        query.executeUpdate();
    }

}
