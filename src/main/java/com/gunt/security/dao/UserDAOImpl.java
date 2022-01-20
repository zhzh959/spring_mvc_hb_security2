package com.gunt.security.dao;


import com.gunt.security.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    User user = new User();

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = entityManager.createQuery("from User", User.class).getResultList();
        return allUsers;
    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        Query query = entityManager.createQuery("delete from User where id=:usId");
        query.setParameter("usId", id);
        query.executeUpdate();
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id );
    }

    @Override
    public User getUserByName(String name) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u where u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return user;
        } catch (NoResultException ex) {
            return null;
        }
    }
}

