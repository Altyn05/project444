package com.amr.project.service;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReadWriteServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void existsById() {
        assertTrue(userService.existsById(User.class, 2L));
        assertFalse(userService.existsById(User.class, 10000000L));
    }

    @Test
    void getByKey() {
        assertTrue(userService.getByKey(User.class, 2L).isPresent());
        assertTrue(userService.getByKey(User.class, 2000000L).isEmpty());
    }

    @Test
    void persist() {
        User user = new User();
        user.setUsername("firstTestUser");
        userService.persist(user);
        assertEquals(user, userService.findUserByUsername("firstTestUser"));
    }

    @Test
    void update() {
        userService.getByKey(User.class, 1L).ifPresent(user -> {
            user.setUsername("updateUser");
            userService.update(user);
            assertEquals(user, userService.findUserByUsername("updateUser"));
        });
    }

    @Test
    void delete() {
        userService.getByKey(User.class, 10L).ifPresent(user -> userService.delete(user));
        assertFalse(userService.existsById(User.class, 10L));
    }

    @Test
    void getAll() {
        assertTrue(userService.getAll(User.class).size() > 0);
    }
}