package com.amr.project.service.abstracts;

import com.amr.project.model.entity.User;

import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long> {

    void registerNewUser(User user);
    User findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByIdProvider(String id);
}
