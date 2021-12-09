package com.amr.project.service;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.util.EmailUtil;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class
UserServiceTest {

    private final UserService userService;

    @MockBean
    private UserDao userDao;

    @MockBean
    private RoleService roleService;

    @MockBean
    private EmailUtil emailUtil;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void registerNewUserTest(){
        User user = new User();
        user.setEmail("test@test.com");
        Set<Role> role = new HashSet<>();
        role.add(roleService.findById(2L));

        userService.registerNewUser(user);
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(role));
        Assertions.assertFalse(user.isActivate());
        Mockito.verify(userDao, Mockito.times(1)).persist(user);

        Mockito.verify(emailUtil, Mockito.times(1))
                .sendMessage(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }
}
