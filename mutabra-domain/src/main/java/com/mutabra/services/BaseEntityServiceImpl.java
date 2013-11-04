package com.mutabra.services;

import org.mongodb.morphia.Datastore;
import com.mutabra.domain.BaseEntity;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityServiceImpl<E extends BaseEntity>
        extends EntityServiceImpl<E, ObjectId>
        implements BaseEntityService<E> {

    public BaseEntityServiceImpl(final Datastore datastore, final Class<E> entityClass) {
        super(datastore, entityClass);
    }
}
