package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.RoleDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final EmailUtil emailUtil;

    public UserServiceImpl(ReadWriteDao<User, Long> readWriteDao, UserDao userDao, RoleService roleService, EmailUtil emailUtil) {
        super(readWriteDao);
        this.userDao = userDao;
        this.roleService = roleService;
        this.emailUtil = emailUtil;
    }

    @Override
    public void registerNewUser(User user) {
        Set<Role> role = new HashSet<>();
        role.add(roleService.findById(1L));
        user.setRoles(role);
        user.setActivationCode(UUID.randomUUID().toString());
        emailUtil.sendMessage(user.getEmail(), "Это активация",
                "Для активации перейдите по ссылке \n" +
                        "http://localhost:8888/activate/" + user.getActivationCode());
        userDao.persist(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public User findUserByActivationCode(String activationCode) {
        return userDao.findUserByActivationCode(activationCode);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }
}
