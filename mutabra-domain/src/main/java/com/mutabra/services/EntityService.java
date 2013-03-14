package com.mutabra.services;

import com.mutabra.domain.Entity;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EntityService<E extends Entity<PK>, PK extends Serializable> {

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
    E get(PK pk);

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
