package com.amr.project.service.abstracts;

import com.amr.project.model.entity.User;

public interface UserService extends ReadWriteService<User, Long> {

    void registerNewUser(User user);

    User findUserByUsername(String username);

    User findUserByActivationCode(String activationCode);

    User findById(Long id);
}
