package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.User;

import java.util.Optional;

public interface UserDao extends ReadWriteDao<User, Long> {
    User findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByIdProvider(String id);
}
