package com.epam.lab.repository.hibernate.repository.impl;


import com.epam.lab.model.entities.Roles;
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
public class RoleRepository extends AbstractRepository<Roles> {


    public Roles save(Roles entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity;
    }

    public Roles update(Roles entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    public List<Roles> saveAll(List<Roles> entity) {
        entity.forEach(a -> sessionFactory.getCurrentSession().save(a));
        return entity;
    }

    public List<Roles> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Roles> cq = cb.createQuery(Roles.class);
        Root<Roles> root = cq.from(Roles.class);
        cq.select(root);
        Query<Roles> query = session.createQuery(cq);
        return query.getResultList();
    }

    public Optional<Roles> findById(int id) {
        Roles roles = sessionFactory.getCurrentSession().get(Roles.class, id);
        return Optional.of(roles);
    }

    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Roles roles = session.byId(Roles.class).load(id);
        session.delete(roles);
    }

    public void deleteAll() {
        String q = "TRUNCATE roles RESTART IDENTITY CASCADE";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(q);
        query.executeUpdate();
    }

}
