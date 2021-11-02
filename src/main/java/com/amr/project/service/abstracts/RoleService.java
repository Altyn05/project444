package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Role;

import java.util.Optional;

public interface RoleService extends ReadWriteService<Role, Long> {
    Optional<Role> getRoleByName(String name);
}
