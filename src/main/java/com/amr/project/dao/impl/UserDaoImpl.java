package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {
    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        return em.createQuery("select c from User c where c.username like :username", User.class).setParameter("username", username).getSingleResult();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return SingleResultUtil.getSingleResultOrNull(
                em.createQuery("select u from User u where u.email = :email", User.class).setParameter("email", email));
    }

    @Override
    public Optional<User> findUserByIdProvider(String id) {
        return SingleResultUtil.getSingleResultOrNull(
                em.createQuery("select u from User u where u.idProvider = :id", User.class).setParameter("id", id));
    }

    public User findUserByActivationCode(String activationCode) {
        return (User) em.createQuery("Select e FROM User e WHERE e.activationCode = :activationCode")
                .setParameter("activationCode", activationCode)
                .getSingleResult();
    }
}
