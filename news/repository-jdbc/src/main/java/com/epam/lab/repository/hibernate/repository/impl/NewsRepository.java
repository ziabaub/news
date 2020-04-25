package com.epam.lab.repository.hibernate.repository.impl;


import com.epam.lab.model.entities.News;
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
public class NewsRepository extends AbstractRepository<News> {

    public News save(News entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity;
    }

    public News update(News entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    public List<News> saveAll(List<News> entity) {
        entity.forEach(a -> sessionFactory.getCurrentSession().save(a));
        return entity;
    }

    public List<News> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<News> cq = cb.createQuery(News.class);
        Root<News> root = cq.from(News.class);
        cq.select(root);
        Query<News> query = session.createQuery(cq);
        return query.getResultList();
    }

    public Optional<News> findById(int id) {
        News news = sessionFactory.getCurrentSession().get(News.class, id);
        return Optional.of(news);
    }

    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        News news = session.byId(News.class).load(id);
        session.delete(news);
    }

    public void deleteAll() {
        String q = "TRUNCATE news RESTART IDENTITY CASCADE";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(q);
        query.executeUpdate();
    }

    public Object count() {
        String q = "SELECT COUNT(*) FROM news ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(q);
        return query.getSingleResult();
    }
}
