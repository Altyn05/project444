package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Role;

public interface RoleDao extends ReadWriteDao<Role, Long>{

    Role getRoleById(Long id);

    Role getRoleByName(String name);
}
