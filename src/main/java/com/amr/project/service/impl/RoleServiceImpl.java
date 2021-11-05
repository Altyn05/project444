package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.RoleDao;
import com.amr.project.model.entity.Role;
import com.amr.project.service.abstracts.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends ReadWriteServiceImpl<Role, Long> implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(ReadWriteDao<Role, Long> readWriteDao, RoleDao roleDao) {
        super(readWriteDao);
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    public Role findById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.getRoleByName(name);
    }

}
