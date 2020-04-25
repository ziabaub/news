package com.epam.lab.repository.hibernate.repository.impl;


import com.epam.lab.model.entities.User;
import com.epam.lab.repository.hibernate.repository.AbstractRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
@Profile(value = "hibernate")
public class UserRepository extends AbstractRepository<User> {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User entity) {
        String encodedPass = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPass);
        sessionFactory.getCurrentSession().save(entity);
        sessionFactory.getCurrentSession().flush();
        return entity;
    }

    public User update(User entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    public List<User> saveAll(List<User> entity) {
        entity.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            sessionFactory.getCurrentSession().save(user);
        });
        sessionFactory.getCurrentSession().flush();
        return entity;
    }

    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        Query<User> query = session.createQuery(cq);
        return query.getResultList();
    }

    public Optional<User> findById(int id) {
        User user = sessionFactory.getCurrentSession().get(User.class, id);
        return Optional.of(user);
    }

    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.byId(User.class).load(id);
        session.delete(user);
    }

    public void deleteAll() {
        String q = "TRUNCATE users RESTART IDENTITY CASCADE";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(q);
        query.executeUpdate();
    }

    @Override
    public User findBy(String login) {
        return sessionFactory.openSession().createNamedQuery("users.findByLogin", User.class)
                .setParameter("login", login)
                .getSingleResult();
    }

}
