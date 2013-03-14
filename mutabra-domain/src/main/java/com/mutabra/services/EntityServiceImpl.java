package com.mutabra.services;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.mutabra.domain.Entity;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityServiceImpl<E extends Entity<PK>, PK extends Serializable> implements EntityService<E, PK> {

    private final BasicDAO<E, PK> dao;

    public EntityServiceImpl(final Datastore datastore, final Class<E> entityClass) {
        dao = new BasicDAO<E, PK>(entityClass, datastore);
    }

    public void save(final E entity) {
        dao.save(entity);
    }

    public void delete(final E entity) {
        dao.delete(entity);
    }

    public Class<E> getEntityClass() {
        return dao.getEntityClass();
    }

    public E get(final PK pk) {
        return dao.get(pk);
    }

    protected BasicDAO<E, PK> dao() {
        return dao;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + dao.getEntityClass() + "]";
    }
}
