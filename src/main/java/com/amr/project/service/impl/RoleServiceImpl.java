package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.RoleDao;
import com.amr.project.model.entity.Role;
import com.amr.project.service.abstracts.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl extends ReadWriteServiceImpl<Role, Long> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    protected RoleServiceImpl(ReadWriteDao<Role, Long> readWriteDao) {
        super(readWriteDao);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

}
