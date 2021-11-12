package com.amr.project.service.impl;

import com.amr.project.model.entity.Role;
import com.amr.project.service.abstracts.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleServiceImplTest {

    @Autowired
    private RoleService service;

    @Test
    public void getByKey() {
        assertEquals("ADMIN", service.getByKey(Role.class, 1L).get().getName());
        assertFalse(service.getByKey(Role.class, 5L).isPresent());
    }

    @Test
    public void findByName() {
        assertEquals("ADMIN", service.findByName("ADMIN").getName());
    }

    @Test
    public void persist() {
        Role role = new Role("NONE");
        service.persist(role);
        assertNotNull(role.getId());
        assertNotNull(service.findByName("NONE"));
    }

    @Test
    public void delete() {
        service.delete(service.findByName("USER"));
        assertNull(service.findByName("USER"));
    }
}
