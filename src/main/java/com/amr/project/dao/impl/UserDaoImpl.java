package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {
    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        List<User> arr = em.createQuery("select c from User c where c.username like :username", User.class).setParameter("username", username).getResultList();// getSingleResult();
          for (User u : arr) {
              System.out.println(u.getId() + " " + u.getUsername());
              Set<Role> roleSet= u.getRoles();
              for (Role role : roleSet){
                  System.out.println(role.getName());
              }

          }
       // return em.createQuery("select c from User c where c.username like :username", User.class).setParameter("username", username). getSingleResult();
       return arr.get(0);
    }

    @Override
    public User findUserByActivationCode(String activationCode) {
        return (User) em.createQuery("Select e FROM User e WHERE e.activationCode = :activationCode")
                .setParameter("activationCode", activationCode)
                .getSingleResult();
    }
}
