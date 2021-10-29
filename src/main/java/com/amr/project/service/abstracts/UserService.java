package com.amr.project.service.abstracts;

import com.amr.project.model.entity.User;

import java.util.Optional;

public interface UserService {

    void registerNewUser(User user);
    User findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
