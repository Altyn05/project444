package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Role;

import java.util.Optional;

public interface RoleDao extends ReadWriteDao<Role, Long>{

    Role getRoleById(Long id);
    Optional<Role> getRoleByName(String name);
}
