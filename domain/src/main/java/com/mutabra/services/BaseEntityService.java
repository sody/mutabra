package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BaseEntityService<E extends BaseEntity> extends EntityService<E, ObjectId> {

    /**
     * Gets entity class, service working with.
     *
     * @return entity class
     */
    Class<E> getEntityClass();

    /**
     * Gets detailed entity selected by primary key.
     *
     * @param pk entity primary key
     * @return detailed entity selected by primary key
     */
    E get(ObjectId pk);

    /**
     * Inserts detailed entity into repository.
     *
     * @param entity detailed entity
     */
    void save(E entity);

    /**
     * Deletes detailed entity from repository
     *
     * @param entity detailed entity
     */
    void delete(E entity);
}
