package com.amr.project.service.impl;

import com.amr.project.model.entity.Role;
import com.amr.project.service.abstracts.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoleServiceImplTest {

    @Autowired
    private RoleService service;

    @Test
    public void persist() {
        Role role = new Role("NONE");
        service.persist(role);
        assertNotNull(role.getId());
    }

    @Test
    public void getByKey() {
        assertFalse(service.getByKey(Role.class, 5L).isPresent());
        assertEquals("ADMIN", service.getByKey(Role.class, 1L).get().getName());
    }
}