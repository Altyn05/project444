package com.amr.project.service.abstracts;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReadWriteService<T, ID> {

    void persist(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteByKeyCascadeEnable(Class<T> clazz, ID id);

    void deleteByKeyCascadeIgnore(Class<T> clazz, ID id);

    boolean existsById(Class<T> clazz, ID id);

    Optional<T> getByKey(Class<T> clazz, ID id);

    List<T> getAll(Class<T> clazz);

}
