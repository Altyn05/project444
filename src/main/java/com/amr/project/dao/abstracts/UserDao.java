package com.amr.project.dao.abstracts;


import com.amr.project.model.entity.User;

public interface UserDao extends ReadWriteDao<User, Long> {
    User findById(Long id);
//    User findByName(String name);
    User findUserByUsername(String username);
    User findUserByActivationCode(String activationCode);
}
