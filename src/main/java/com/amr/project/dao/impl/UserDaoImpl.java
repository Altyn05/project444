package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

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
    public User findUserByActivationCode(String activationCode) {
        return (User) em.createQuery("Select e FROM User e WHERE e.activationCode = :activationCode")
                .setParameter("activationCode", activationCode)
                .getSingleResult();
    }
}
