package com.epam.lab.repository.hibernate.repository.impl;


import com.epam.lab.model.entities.Tag;
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
public class TagRepository extends AbstractRepository<Tag> {

    public Tag save(Tag entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity;
    }

    public Tag update(Tag entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    public List<Tag> saveAll(List<Tag> entity) {
        entity.forEach(a -> sessionFactory.getCurrentSession().save(a));
        return entity;
    }

    public List<Tag> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> root = cq.from(Tag.class);
        cq.select(root);
        Query<Tag> query = session.createQuery(cq);
        return query.getResultList();
    }

    public Optional<Tag> findById(int id) {
        Tag tag = sessionFactory.getCurrentSession().get(Tag.class, id);
        return Optional.of(tag);
    }

    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Tag tag = session.byId(Tag.class).load(id);
        session.delete(tag);
    }

    public void deleteAll() {
        String q = "TRUNCATE tag RESTART IDENTITY CASCADE";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(q);
        query.executeUpdate();
    }

}
