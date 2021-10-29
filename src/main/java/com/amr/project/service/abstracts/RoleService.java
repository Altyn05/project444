package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Role;

public interface RoleService extends ReadWriteService<Role, Long> {
    Role getRoleByName(String name);
}
