package com.mutabra.services;

import com.mutabra.annotations.Transactional;
import com.mutabra.domain.BaseEntity;
import org.greatage.domain.Repository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BaseEntityService<E extends BaseEntity> {

    /**
     * Gets entity class, service working with.
     *
     * @return entity class
     */
    Class<E> getEntityClass();

    /**
     * Gets entity name, service working with.
     *
     * @return entity name
     */
    String getEntityName();

    /**
     * Gets detailed entity selected by primary key.
     *
     * @param pk entity primary key
     * @return detailed entity selected by primary key
     */
    E get(Long pk);

    /**
     * Creates detailed entity filled with default values.
     *
     * @return detailed entity filled with default values
     */
    E create();

    /**
     * Inserts detailed entity into repository.
     *
     * @param entity detailed entity
     */
    @Transactional
    void save(E entity);

    /**
     * Updates detailed entity in repository.
     *
     * @param entity detailed entity
     */
    @Transactional
    void update(E entity);

    /**
     * Inserts or updates detailed entity in repository.
     *
     * @param entity detailed entity
     */
    @Transactional
    void saveOrUpdate(E entity);

    /**
     * Deletes detailed entity from repository
     *
     * @param entity detailed entity
     */
    @Transactional
    void delete(E entity);

    Repository.Query<Long, E> query();

}
