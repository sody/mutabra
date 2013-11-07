package com.mutabra.services;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import com.mutabra.domain.Entity;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityServiceImpl<E extends Entity<PK>, PK extends Serializable> implements EntityService<E, PK> {

    private final Datastore datastore;
    private final Class<E> entityClass;

    public EntityServiceImpl(final Datastore datastore, final Class<E> entityClass) {
        this.datastore = datastore;
        this.entityClass = entityClass;
    }

    public void save(final E entity) {
        datastore.save(entity);
    }

    public void delete(final E entity) {
        datastore.delete(entity);
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public E get(final PK pk) {
        return datastore.get(entityClass, pk);
    }

    public Query<E> query() {
        return datastore.createQuery(entityClass);
    }

    protected Datastore datastore() {
        return datastore;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + entityClass + "]";
    }
}
