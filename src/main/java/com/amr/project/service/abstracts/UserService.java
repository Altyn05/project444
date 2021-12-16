package com.amr.project.service.abstracts;


import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;

import java.util.List;
import java.util.Optional;
public interface UserService extends ReadWriteService<User, Long> {
    User findById(Long id);
    void registerNewUser(User user);


        boolean createNewUser(User user);

        User findUserByUsername(String username);

        Optional<User> findUserByEmail(String email);

        Optional<User> findUserByIdProvider(String id);

        User findUserByActivationCode(String activationCode);

        boolean checkByUsername(String name);

        void updateUser(User user);

        UserDto show();

        List<User> findUsersByEMail(String eMail);

    }

