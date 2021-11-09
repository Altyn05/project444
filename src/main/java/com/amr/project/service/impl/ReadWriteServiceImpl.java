package com.amr.project.service.impl;


import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class ReadWriteServiceImpl<T, ID> implements ReadWriteService<T, ID> {

    private final ReadWriteDao<T, ID> readWriteDao;

    @Autowired
    protected ReadWriteServiceImpl(ReadWriteDao<T, ID> readWriteDao) {
        this.readWriteDao = readWriteDao;
    }

    @Override
    public void persist(T entity) {
        readWriteDao.persist(entity);
    }

    @Override
    public void update(T entity) {
        readWriteDao.update(entity);
    }

    @Override
    public void delete(T entity) {
        readWriteDao.delete(entity);
    }

    @Override
    public void deleteByKeyCascadeEnable(Class<T> clazz, ID id) {
        readWriteDao.deleteByKeyCascadeEnable(clazz, id);
    }

    @Override
    public void deleteByKeyCascadeIgnore(Class<T> clazz, ID id) {
        readWriteDao.deleteByKeyCascadeIgnore(clazz, id);
    }

    @Override
    public boolean existsById(Class<T> clazz, ID id) {
        return readWriteDao.existsById(clazz, id);
    }

    @Override
    public Optional<T> getByKey(Class<T> clazz, ID id) {
        return readWriteDao.getByKey(clazz, id);
    }

    @Override
    public List<T> getAll(Class<T> clazz) {
        return readWriteDao.getAll(clazz);
    }
}
