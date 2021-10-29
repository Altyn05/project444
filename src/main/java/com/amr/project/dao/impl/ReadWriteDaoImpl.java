package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import org.hibernate.metamodel.internal.MetamodelImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public abstract class ReadWriteDaoImpl<T, ID> implements ReadWriteDao<T, ID> {

    @PersistenceContext
    protected EntityManager em;
    private final String ID_MESSAGE = "id";
    private final String ENTITY_MESSAGE = "entity";
    private final String NULL_MESSAGE = " parameters should not be null!";

    @Override
    @Transactional
    public void persist(@NotNull T entity) {
        Objects.requireNonNull(entity, ENTITY_MESSAGE + NULL_MESSAGE);
        em.persist(entity);
    }

    @Override
    @Transactional
    public void update(@NotNull T entity) {
        Objects.requireNonNull(entity, ENTITY_MESSAGE + NULL_MESSAGE);
        em.merge(entity);
    }

    @Override
    @Transactional
    public void delete(@NotNull T entity) {
        Objects.requireNonNull(entity, ENTITY_MESSAGE + NULL_MESSAGE);
        em.remove(entity);
    }

    @Override
    @Transactional
    public void deleteByKeyCascadeEnable(@NotNull Class<T> clazz, @NotNull ID id) {
        Objects.requireNonNull(id, ID_MESSAGE + NULL_MESSAGE);
        getByKey(clazz, id).ifPresent(this::delete);
    }

    @Override
    @Transactional
    public void deleteByKeyCascadeIgnore(@NotNull Class<T> clazz, @NotNull ID id) {
        Objects.requireNonNull(id, ID_MESSAGE + NULL_MESSAGE);
        em.createNativeQuery("delete from " + extractTableName(clazz) +" where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(@NotNull Class<T> clazz, @NotNull ID id) {
        Objects.requireNonNull(id, ID_MESSAGE + NULL_MESSAGE);
        return getByKey(clazz, id).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> getByKey(@NotNull Class<T> clazz, @NotNull ID id) {
        Objects.requireNonNull(id, ID_MESSAGE + NULL_MESSAGE);
        return Optional.ofNullable(em.find(clazz, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll(@NotNull Class<T> clazz) {
        return em.createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    private String extractTableName(Class<?> modelClazz) {
        final MetamodelImpl metamodel = (MetamodelImpl) em.getMetamodel();
        final EntityPersister entityPersister = metamodel.entityPersister(modelClazz);
        if (entityPersister instanceof SingleTableEntityPersister) {
            return ((SingleTableEntityPersister) entityPersister).getTableName();
        } else {
            throw new IllegalArgumentException(modelClazz + " does not map to a single table.");
        }
    }

    protected Pageable pageCheck(long size, Pageable p) {
        if (p.isUnpaged()) {
            return p;
        }
        long lastPage = (size - 1) / p.getPageSize();
        if (p.getOffset() > lastPage * p.getPageSize()) {
            return PageRequest.of((int) lastPage, p.getPageSize());
        }
        return p;
    }
}
