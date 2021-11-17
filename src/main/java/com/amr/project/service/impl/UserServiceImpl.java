package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.AddressDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.RoleDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {
    private UserDao userDao;
    private RoleDao roleDao;
    private AddressDao addressDao;
   private final RoleService roleService;
   private final EmailUtil emailUtil;
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

    public UserServiceImpl(RoleService roleService, RoleDao roleDao, AddressDao addressDao, EmailUtil emailUtil, ReadWriteDao<User, Long> readWriteDao, UserDao userDao) {
        super(readWriteDao);
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.addressDao = addressDao;
       this.roleService = roleService;
        this.emailUtil = emailUtil;

    }

    @Override
    public void updateUser(User user) {

        userDao.update(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
        emailUtil.sendMessage(
                user.getEmail(),
                "Редактирование профиля",
                "Ваш профиль " + user.getUsername() + " был изменён."
        );
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
        emailUtil.sendMessage(
                user.getEmail(),
                "Удаление профиля",
                "Ваш профиль " + user.getUsername() + " был удалён."
        );
    }

    @Override
    public boolean createNewUser(User user) {
        System.out.println(user);
        if (userDao.checkByUsername(user.getUsername())) {
            user.addRole(roleDao.getRoleById(2L));
            user.setActivate(true);
            userDao.persist(user);
      return true;  }
  return false;  }

public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
}

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByIdProvider(String id) {
        return userDao.findUserByIdProvider(id);
    }

    public User findUserByActivationCode(String activationCode) {
        return userDao.findUserByActivationCode(activationCode);
    }

    public boolean checkByUsername(String username) {

        return userDao.checkByUsername(username);
    }
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }
}
